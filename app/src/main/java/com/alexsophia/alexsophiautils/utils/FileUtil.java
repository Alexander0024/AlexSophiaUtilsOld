package com.alexsophia.alexsophiautils.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.util.Base64;
import android.widget.Toast;

import com.alexsophia.alexsophiautils.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * 文件处理帮助类
 *
 * @author liuweiping
 */
public final class FileUtil {
    private static final String TAG = "FileUtil";

    /**
     * 打开文件
     *
     * @param context
     * @param path
     */
    public final static void openFile(Context context, String path) {
        File file = new File(path);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        String type = getMIMEType(file);
        intent.setDataAndType(Uri.fromFile(file), type);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            ToastUtil.show(context, R.string._no_open_file, Toast.LENGTH_SHORT);
            e.printStackTrace();
        }
    }

    /**
     * 递归创建多级文件目录
     */
    public final static boolean mkDir(File file) {
        if (file != null && file.exists()) {
            return true;
        }
        if (null != file.getParentFile() && file.getParentFile().exists()) {
            LogWrapper.e("FileUtil", "存在");
            boolean success = file.mkdir();
            LogWrapper.e("FileUtil", success + "创建目录成功");
            return success;
        } else {
            LogWrapper.e("FileUtil", "不存在");
            mkDir(file.getParentFile());
            return file.mkdir();
        }
    }

    /**
     * 删除文件夹
     *
     * @param folderPath 文件夹完整绝对路径
     */
    public final static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径
     * @return
     */
    public final static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        if (null == tempList || tempList.length == 0) {
            return flag;
        }
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
                LogWrapper.i(TAG, "Delete " + temp.getPath());
            }
            if (temp.isDirectory()) {
                delAllFile(path + File.separator + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + File.separator + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 删除空目录
     * <p/>
     * 返回 0代表成功 ,1 代表没有删除权限, 2代表不是空目录,3 代表未知错误
     *
     * @return
     */
    public static int deleteBlankPath(String path) {
        File f = new File(path);
        if (!f.canWrite()) {
            return 1;
        }
        if (f.list() != null && f.list().length > 0) {
            return 2;
        }
        if (f.delete()) {
            return 0;
        }
        return 3;
    }

    /**
     * 写文本文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
     *
     * @param context
     */
    public static void write(Context context, String fileName, String content) {
        if (content == null)
            content = "";

        try {
            FileOutputStream fos = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            fos.write(content.getBytes());

            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文本文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String read(Context context, String fileName) {
        try {
            FileInputStream in = context.openFileInput(fileName);
            return readInStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String readInStream(InputStream inStream) {
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];
            int length = -1;
            while ((length = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, length);
            }

            outStream.close();
            inStream.close();
            return outStream.toString();
        } catch (IOException e) {
            LogWrapper.e("FileTest", e.getMessage());
        }
        return null;
    }

    /**
     * 根据文件绝对路径获取文件名
     *
     * @param filePath
     * @return
     */
    public final static String getFileName(String filePath) {
        if (filePath == null || "".equals(filePath))
            return "";
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
    }

    /**
     * 根据文件的绝对路径获取文件名但不包含扩展名
     *
     * @param filePath
     * @return
     */
    public final static String getFileNameNoFormat(String filePath) {
        if (filePath == null || "".equals(filePath)) {
            return "";
        }
        int point = filePath.lastIndexOf('.');
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1,
                point);
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    public final static String getFileFormat(String fileName) {
        if (fileName == null || "".equals(fileName))
            return "";
        int point = fileName.lastIndexOf('.');
        return fileName.substring(point + 1);
    }

    /**
     * 获取文件大小
     *
     * @param filePath
     * @return
     */
    public final static long getFileSize(String filePath) {
        long size = 0;

        File file = new File(filePath);
        if (file != null && file.exists()) {
            size = file.length();
        }
        return size;
    }

    /**
     * 获取文件大小
     *
     * @param size 字节
     * @return
     */
    public final static String getFileSize(long size) {
        if (size <= 0)
            return "0";
        java.text.DecimalFormat df = new java.text.DecimalFormat("##.##");
        float temp = (float) size / 1024;
        if (temp >= 1024) {
            return df.format(temp / 1024) + "M";
        } else {
            return df.format(temp) + "K";
        }
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return B/KB/MB/GB
     */
    public final static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS == 0) {
            fileSizeString = "0B";
        } else if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public final static long getDirSize(File dir) {
        if (dir == null || !dir.exists()) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        if (null == files || files.length == 0) {
            return 0;
        }
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 获取制定目录下所有的文件路径集合
     */
    public final static List<String> getDirFilePaths(String dir) {
        File root = new File(dir);
        if (!root.exists()) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        if (root.isDirectory()) {
            getFilePath(root, list);
        } else {
            list.add(dir);
        }
        return list;
    }

    /**
     * 获得一个文件的上一级目录
     *
     * @param path
     * @return
     */

    public static String getParentPath(String path) {
        File file = new File(path);
        return file.getParent();
    }

    private final static void getFilePath(File root, List<String> list) {
        File[] files = root.listFiles();
        if (null == files || files.length == 0) {
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                getFilePath(f, list);
            } else {
                list.add(f.getAbsolutePath());
            }
        }
    }

    public final static boolean isPhoto(String path) {
        return path != null
                && (path.endsWith(".png") || path.endsWith(".PNG")
                || path.endsWith(".jpg") || path.endsWith(".JPG")
                || path.endsWith(".jpeg") || path.endsWith(".JPEG")
                || path.endsWith(".note") || path.endsWith(".NOTE")
                || path.endsWith(".bmp") || path.endsWith(".BMP"));
    }

    public final static boolean isInote(String path) {
        return path != null
                && (path.endsWith(".inote") || path.endsWith(".INOTE"));
    }

    /**
     * 获取目录文件个数
     *
     * @return
     */
    public final static long getFileList(File dir) {
        long count = 0;
        if (dir == null || !dir.exists()) {
            return count;
        }
        File[] files = dir.listFiles();
        if (null == files || files.length == 0) {
            return count;
        }
        count = files.length;
        for (File file : files) {
            if (file.isDirectory()) {
                count = count + getFileList(file);// 递归
                count--;
            }
        }
        return count;
    }

    public final static int getFileCount(File dir) {
        int count = 0;
        if (dir == null || !dir.exists()) {
            return count;
        }
        File[] files = dir.listFiles();
        if (null == files || files.length == 0) {
            return count;
        }
        count = files.length;
        for (File file : files) {
            if (file.isDirectory()) {
                count = count + getFileCount(file);// 递归
                count--;
            }
        }
        return count;
    }

    private final static String[][] Filetype = {
            {"zip", "application/vnd-note"},
            {"note", "application/vnd-note"},
            {"apk", "application/vnd.android.package-archive"},
            {"epub", "application/xhtml+xml"},
            {"3gp", "video/3gpp"},
            {"apk", "application/vnd.android.package-archive"},
            {"asf", "video/x-ms-asf"},
            {"avi", "video/x-msvideo"},
            {"flv", "video/x-msvideo"},
            {"bin", "application/octet-stream"},
            {"bmp", "image/bmp"},
            {"c", "text/plain"},
            {"class", "application/octet-stream"},
            {"conf", "text/plain"},
            {"cpp", "text/plain"},
            {"doc", "application/msword"},
            {"DOC", "application/msword"},
            {"docx",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {"DOCX",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {"xls", "application/vnd.ms-excel"},
            {"XLS", "application/vnd.ms-excel"},
            {"xlsx",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {"XLSX",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {"exe", "application/octet-stream"},
            {"gif", "image/gif"},
            {"gtar", "application/x-gtar"},
            {"gz", "application/x-gzip"},
            {"h", "text/plain"},
            {"htm", "text/html"},
            {"html", "text/html"},
            {"jar", "application/java-archive"},
            {"java", "text/plain"},
            {"jpeg", "image/jpeg"},
            {"JPEG", "image/jpeg"},
            {"jpg", "image/jpeg"},
            {"JPG", "image/jpeg"},
            {"js", "application/x-javascript"},
            {"log", "text/plain"},
            {"m3u", "audio/x-mpegurl"},
            {"m4a", "audio/mp4a-latm"},
            {"m4b", "audio/mp4a-latm"},
            {"m4p", "audio/mp4a-latm"},
            {"m4u", "video/vnd.mpegurl"},
            {"m4v", "video/x-m4v"},
            {"mov", "video/quicktime"},
            {"mp2", "audio/x-mpeg"},
            {"mp3", "audio/x-mpeg"},
            {"mp4", "video/mp4"},
            {"mpc", "application/vnd.mpohun.certificate"},
            {"mpe", "video/mpeg"},
            {"mpeg", "video/mpeg"},
            {"mpg", "video/mpeg"},
            {"mpg4", "video/mp4"},
            {"mpga", "audio/mpeg"},
            {"msg", "application/vnd.ms-outlook"},
            {"ogg", "audio/ogg"},
            {"pdf", "application/pdf"},
            {"png", "image/png"},
            {"PNG", "image/png"},
            {"pps", "application/vnd.ms-powerpoint"},
            {"ppt", "application/vnd.ms-powerpoint"},
            {"PPT", "application/vnd.ms-powerpoint"},
            {"pptx",
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {"prop", "text/plain"}, {"rc", "text/plain"},
            {"rmvb", "audio/x-pn-realaudio"}, {"rtf", "application/rtf"},
            {"sh", "text/plain"}, {"tar", "application/x-tar"},
            {"tgz", "application/x-compressed"}, {"txt", "text/plain"},
            {"wav", "audio/x-wav"}, {"wma", "audio/x-ms-wma"},
            {"wmv", "audio/x-ms-wmv"}, {"wps", "application/vnd.ms-works"},
            {"xml", "text/plain"}, {"z", "application/x-compress"},
            {"zip", "application/x-zip-compressed"}, {"", "*/*"}};

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    public final static String getFileTypeFormat(String fileName) {
        if (fileName == null || "".equals(fileName))
            return "";

        int point = fileName.lastIndexOf('.');
        return fileName.substring(point);
    }

    /**
     * 根据传进来的文件名截取最后字符串
     *
     * @param filename
     * @return
     */
    public final static String getFileType(String filename) {
        if (filename.indexOf(".") == -1)
            return "";
        String ext = filename.substring(filename.lastIndexOf(".") + 1);
        return ext.trim();
    }

    private final static String getMIMEType(File file) {
        String type = "*/*";
        String fName = file.getName();
        String end = getFileType(fName);
        System.out.println(end + "end");
        for (int i = 0; i < Filetype.length; i++) {
            if (end.equals(Filetype[i][0]))
                type = Filetype[i][1];
        }
        return type;
    }

    /**
     * 获取sd卡的目录
     */
    public final static String getSDCardPath() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        } else {
            return null;
        }
    }

    /**
     * 计算SD卡的剩余空间
     *
     * @return 返回-1，说明没有安装sd卡
     */
    public static long getFreeDiskSpace() {
        String status = Environment.getExternalStorageState();
        long freeSpace = 0;
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getAvailableBlocks();
                freeSpace = availableBlocks * blockSize / 1024;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return -1;
        }
        return (freeSpace);
    }

    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 检查是否安装SD卡
     *
     * @return
     */
    public static boolean checkSaveLocationExists() {
        String sDCardStatus = Environment.getExternalStorageState();
        boolean status;
        if (sDCardStatus.equals(Environment.MEDIA_MOUNTED)) {
            status = true;
        } else
            status = false;
        return status;
    }

    /**
     * 检查是否安装外置的SD卡
     *
     * @return
     */
    public static boolean checkExternalSDExists() {
        Map<String, String> evn = System.getenv();
        return evn.containsKey("SECONDARY_STORAGE");
    }

    /**
     * 按照修改时间的先后进行排序，删除目录下超过 {@code size} 个数的的文件。
     *
     * @param filePath 文件路径String地址
     * @param size     保留的文件个数
     */
    public static void removeOversizedFiles(String filePath, int size) {
        removeOversizedFiles(new File(filePath), size);
    }
    /**
     * 获取默认存储sd卡目录，否则根目录
     *
     * @return
     */
    public static String getSDPath() {
        if (FileUtil.checkSaveLocationExists()) {
            return Environment.getExternalStorageDirectory().getPath();
        } else {
            return Environment.getRootDirectory().toString();
        }
    }

    /**
     * 按照修改时间的先后进行排序，删除目录下超过 {@code size} 个数的的文件。
     *
     * @param filePath 文件路径File对象
     * @param size     保留的文件个数
     */
    public static void removeOversizedFiles(final File filePath, final int size) {
        if (filePath.isDirectory()) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    File[] files = filePath.listFiles();
                    if (null != files && files.length != 0) {
                        sort(files);
                        while (files.length > size) {
                            if (null != files[0]) {
                                files[0].delete();
                                files = filePath.listFiles();
                            }
                        }
                    }
                }
            }).start();
        }
    }

    /**
     * 对文件进行根据修改时间的排序，最新修改的排在末尾
     *
     * @param array 文件数组
     */
    public static void sort(File[] array) {
        File temp = null;
        boolean condition = false;
        for (int i = 0; i < array.length; i++) {
            for (int j = array.length - 1; j > i; j--) {
                condition = array[j].lastModified() < array[j - 1]
                        .lastModified();
                if (condition) {
                    temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                }
            }
        }
    }

    /**
     * 将Bitmap 图片保存到本地路径，并返回路径
     * @param c context
     * @param fileName 文件名称
     * @param bitmap 图片
     * @return 头像在本地的路径
     */
    public static String saveFile(Context c, String filePath, String fileName, Bitmap bitmap) {
        byte[] bytes = bitmapToBytes(bitmap);
        return saveFile(c, filePath, fileName, bytes);
    }

    public static byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static String saveFile(Context c, String filePath, String fileName, byte[] bytes) {
        String fileFullName = "";
        FileOutputStream fos = null;
        String dateFolder = new SimpleDateFormat("yyyyMMdd", Locale.CHINA)
                .format(new Date());
        try {
            String suffix = "";
            if (filePath == null || filePath.trim().length() == 0) {
                filePath = Environment.getExternalStorageDirectory() + "/JiaXT/" + dateFolder + "/";
            }
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File fullFile = new File(filePath, fileName + suffix);
            fileFullName = fullFile.getPath();
            fos = new FileOutputStream(new File(filePath, fileName + suffix));
            fos.write(bytes);
        } catch (Exception e) {
            fileFullName = "";
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    fileFullName = "";
                }
            }
        }
        return fileFullName;
    }

    /**
     * 将文件转成base64 字符串
     *
     * @param path 文件路径
     * @return 文件的base64格式id字符串
     * @throws Exception
     */
    public static String encodeBase64File(String path) throws Exception {
        File  file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }
    /**
     * 检查文件是否存在
     */
    public static boolean fileIsExists(String path){
        try{
            File f=new File(path);
            if(!f.exists()){
                return false;
            }

        }catch (Exception e) {
            return false;
        }
        return true;
    }
}
