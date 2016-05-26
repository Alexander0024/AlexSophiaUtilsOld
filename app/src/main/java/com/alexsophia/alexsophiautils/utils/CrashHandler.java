package com.alexsophia.alexsophiautils.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;

import com.alexsophia.alexsophiautils.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CrashHandler implements UncaughtExceptionHandler {
    private static String TAG = "Log - CrashHandler";
    // 系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例
    private static CrashHandler INSTANCE = new CrashHandler();
    // 程序的Context对象
    private Context mContext;
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<>();
    // 自定义崩溃文件的路径及名称
    private String crashFilePath, crashFileName;
    // 自定义崩溃文件的存储个数
    private int crashFileSize;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler的实例, 单例模式
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 使用默认参数初始化 CrashHandler
     *
     * @param context context
     */
    public void init(Context context) {
        init(context, "", "", 0);
    }

    /**
     * 使用自定义参数初始化 CrashHandler
     *
     * @param context       context
     * @param crashFilePath 保存崩溃文件的路径
     * @param crashFileName 保存崩溃文件的名字
     * @param crashFileSize 保存崩溃文件的个数
     */
    public void init(Context context, String crashFilePath,
                     String crashFileName, int crashFileSize) {
        LogWrapper.i(TAG, "init()");
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        // 设置崩溃文件的路径、文件名及个数的参数
        if (null == crashFilePath || "".equals(crashFilePath)) {
            this.crashFilePath = CommonDirectory.getCrashReportPath();
        } else {
            this.crashFilePath = crashFilePath;
        }
        if (null == crashFileName || "".equals(crashFileName)) {
            this.crashFileName = LogUtils.getDefaultCrashFileName(context);
        } else {
            this.crashFileName = crashFileName;
        }
        if (crashFileSize <= 0) {
            this.crashFileSize = LogUtils.getDefaultCrashFileSize();
        } else {
            this.crashFileSize = crashFileSize;
        }
        LogWrapper.i(TAG, "Path: " + this.crashFilePath + "; File Name: "
                + this.crashFileName + "; File Max Size: " + this.crashFileSize);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            ex.printStackTrace();
            // 退出程序
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                LogWrapper.e(TAG, "Error : " + e);
            }
            LogWrapper.i(TAG, "kill uncaughtException");
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理，收集错误信息，发送错误报告等操作均在此完成.
     *
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        LogWrapper.i(TAG, "handleException()");
        if (ex == null) {
            return false;
        }
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                ToastUtil.showLong(mContext, mContext.getString(R.string.error_crash));
                Looper.loop();
            }

        }.start();
        // 清理多余的崩溃记录
        clearUselessReport();
        // 收集设备参数信息
        collectDeviceInfo(mContext);
        // 保存日志文件
        String crashFile = saveCrashInfo2File(ex);
        // 发送日志到服务器
        sendCrashFile2Server(crashFile);
        return true;
    }

    /**
     * 清理多余的崩溃记录，只保留 {@code crashFileSize} 条
     */
    private void clearUselessReport() {
        LogWrapper.i(TAG, "clearUselessReport()");
        final File path = new File(crashFilePath);
        if (!path.exists()) {
            LogWrapper.i(TAG, "Create: " + path.getPath());
            path.mkdirs();
        }
        FileUtil.removeOversizedFiles(crashFilePath, crashFileSize);
    }

    /**
     * 收集设备参数信息
     */
    public void collectDeviceInfo(Context ctx) {
        LogWrapper.i(TAG, "collectDeviceInfo()");
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            LogWrapper.e(TAG, "an error occurred when collect package info" + e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                LogWrapper.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                LogWrapper.e(TAG, "an error occurred when collect crash info" + e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @return 返回保存文件的完整路径及文件名，便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {
        LogWrapper.i(TAG, "saveCrashInfo2File()");
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                FileOutputStream fos = new FileOutputStream(crashFilePath
                        + crashFileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return crashFilePath + crashFileName;
        } catch (Exception e) {
            LogWrapper.e(TAG, "an error occured while writing file..." + e);
        }
        return null;
    }

    /**
     * 发送日志到服务器，后续与需求确认后添加
     *
     * @param crashFile Crash报告的文件在SD卡的路径
     */
    private void sendCrashFile2Server(String crashFile) {
        LogWrapper.i(TAG, "sendCrashFile2Server()" + crashFile);
    }
}