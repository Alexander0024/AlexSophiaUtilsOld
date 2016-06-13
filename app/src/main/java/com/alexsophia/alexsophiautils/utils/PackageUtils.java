package com.alexsophia.alexsophiautils.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 获取当前软件信息的类
 * Created by Alexander on 2016/3/16.
 */
public class PackageUtils {
    /**
     * 获取版本号
     *
     * @param context Context
     * @return 当前版本号
     */
    public static Integer getVersionCode(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取版本名称
     *
     * @param context Context
     * @return 当前版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return "V" + info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取显示的版本名称：只显示前三位
     *
     * @param context Context
     * @return 当前版本名称的前三位
     */
    public static String getVersionNameForDisplay(Context context) {
        String value = getVersionName(context);
        if (StringUtil.isEmpty(value)) {
            return "";
        } else {
            return value.substring(0, value.lastIndexOf('.'));
        }
    }

    /**
     * 判断设备是否含有相机
     * @param context Context
     * @return hasSystemFeature(PackageManager.FEATURE_CAMERA)
     */
    public static boolean hasCamera(Context context) {
        PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
}
