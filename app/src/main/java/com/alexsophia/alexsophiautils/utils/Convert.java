/**
 * 
 */
package com.alexsophia.alexsophiautils.utils;

import java.math.BigDecimal;

/**
 * @author 刘卫平 2015-4-24 上午10:59:00
 */
public class Convert {
    /**
     * 将整形进度转换成%形式
     * 
     * @param arg
     * @return
     */
    public static String int2double(int arg) {
        double dou = (double) (Math.round(arg / 1000000.00 * 100) / 100.00);
        return String.valueOf(dou);
    }

    /**
     * 将double型转换成字符串
     * 
     * @param arg
     * @return
     */
    public static String long2double(double arg) {
        double dou = (double) (Math.round(arg / 100.00 * 100));// ������λ��Ч����
        return String.valueOf(dou);
    }

    /**
     * 
     */

    /**
     * 下载的当前进度
     */
    public static float downloadPre(long progress, long totle) {
        if (0 == totle) {
            return 0.00f;
        }
        if (progress >= totle) {
            return 100f;
        }
        float progressDou = 100.0f * progress / totle;
        BigDecimal b = new BigDecimal(progressDou);
        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return f1;
    }

    /**
     * 获取文件大小
     * 
     * @param size
     * 
     * @return
     */
    public static String getSoftSize(double size) {

        BigDecimal bd = new BigDecimal(String.valueOf(size));
        String apkSizeStr = bd.toPlainString();
        // System.out.println(apkSizeStr);
        double apkSize = Double.parseDouble(apkSizeStr);
        if (apkSize < 1024) {
            return String.valueOf(apkSize) + "B";
        } else if (apkSize > 1023 && apkSize < 1024 * 1024) {
            apkSize = apkSize / (1024);
            return long2double(apkSize) + "K";
        } else if (apkSize > 1024 * 1024 - 1 && apkSize < 1024 * 1024 * 1024) {
            apkSize = apkSize / (1024 * 1024);
            return long2double(apkSize) + "M";
        }
        return "0.0M";
    }

    /**
     * 获取文件大小
     * 
     * @param size
     * 
     * @return
     */
    public static String getSoftSize(String size) {

        BigDecimal bd = new BigDecimal(size);
        String apkSizeStr = bd.toPlainString();
        // System.out.println(apkSizeStr);
        double apkSize = Double.parseDouble(apkSizeStr);
        if (apkSize < 1024) {
            return String.valueOf(apkSize) + "B";
        } else if (apkSize > 1023 && apkSize < 1024 * 1024) {
            apkSize = apkSize / (1024);
            return long2double(apkSize) + "K";
        } else if (apkSize > 1024 * 1024 - 1 && apkSize < 1024 * 1024 * 1024) {
            apkSize = apkSize / (1024 * 1024);
            return long2double(apkSize) + "M";
        }
        return "0.0M";
    }

    /**
     * 获取下载次数
     * 
     * @param downloadTimes
     * @return
     */
    public static String getDownloadTimes(String downloadTimes) {
        int downloadTimesInt = Integer.parseInt(downloadTimes);
        if (10000 > downloadTimesInt) {
            return downloadTimes;
        } else {
            return downloadTimesInt / 10000 + "w";
        }
    }
}
