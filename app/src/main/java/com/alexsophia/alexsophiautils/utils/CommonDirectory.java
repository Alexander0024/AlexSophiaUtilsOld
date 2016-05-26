package com.alexsophia.alexsophiautils.utils;

import android.os.Environment;

import java.io.File;

/**
 * 程序使用的目录
 * Created by Alexander on 2016/3/18.
 */
public class CommonDirectory {

    public enum TYPE {
        LEARNING_TASK, USER, UPDATE
    }

    /**
     * 获取主目录 一级目录 /sdcard/liren
     */
    public static String getMainPath() {
        return getSDPath() + File.separator + "liren";
    }

    /**
     * 需要清理的缓存路径
     */
    public static String[] CACHE_LOCATION = {
            getLearningTaskPath(),
            getUserInfoPath(),
            getUpdatePath(),
            getImgCache(),
            getFilePath(),
            getCrashReportPath()
    };

    //设置文件下载的路径
    public static String getFilePath() {
        return getMainPath() + File.separator + "Attachment" + File.separator;
    }

    /**
     * 获取学习任务主目录 二级目录 /sdcard/liren/LearningTask/
     */
    public static String getLearningTaskPath() {
        return getMainPath() + File.separator + "LearningTask" + File.separator;
    }

    /**
     * 获取用户信息存储 二级目录 /sdcard/liren/UserInfo/
     */
    public static String getUserInfoPath() {
        return getMainPath() + File.separator + "UserInfo" + File.separator;
    }

    /**
     * 获取版本更新存储 二级目录 /sdcard/liren/Update/
     */
    public static String getUpdatePath() {
        return getMainPath() + File.separator + "Update" + File.separator;
    }

    /**
     * 获取Image Cache缓存目录 二级目录 /sdcard/liren/ImgCache/
     */
    public static String getImgCache() {
        return getSDPath() + imgCache();
    }

    public static String imgCache() {
        return File.separator + "liren" + File.separator + "ImgCache" + File.separator;
    }

    /**
     * 异常日志目录 二级目录 /sdcard/liren/CrashReport/
     */
    public static String getCrashReportPath() {
        return getMainPath() + File.separator + "CrashReport" + File.separator;
    }

    public static String getH5CachePath() {
        return getMainPath() + File.separator + "WebCache" + File.separator;
    }

    /**
     * 获取默认存储sd卡目录，否则根目录
     */
    public static String getSDPath() {
        if (FileUtil.checkSaveLocationExists()) {
            return Environment.getExternalStorageDirectory().getPath();
        } else {
            return Environment.getRootDirectory().toString();
        }
    }

    private static boolean mkdirs(String path) {
        File file = new File(path);
        return file.mkdirs() || file.isDirectory();
    }

    /**
     * 创建好项目所有的一、二级目录文件夹
     */
    public static void initFileDirectory() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mkdirs(getMainPath())) {
                    mkdirs(getLearningTaskPath());
                    mkdirs(getUserInfoPath());
                    mkdirs(getUpdatePath());
                    mkdirs(getImgCache());
                    mkdirs(getCrashReportPath());
                    mkdirs(getH5CachePath());
                }
            }
        }).start();
    }

    /**
     * 将文件转换为本地路径
     *
     * @param url  返回的URL地址
     * @param type 路径类型
     * @return 转换后的文件所在的本地SD卡地址
     */
    public static String switchToLocalDir(String url, TYPE type) {
        if (type == TYPE.USER) {
            // 为用户头像等图片，直接返回至UserInfo根目录
            return getUserInfoPath() + FileUtil.getFileName(url);
        } else if (type == TYPE.LEARNING_TASK) {
            return getLearningTaskPath() + FileUtil.getFileName(url);
        } else if (type == TYPE.UPDATE) {
            return getUpdatePath() + FileUtil.getFileName(url);
        }
        return "";
    }
}