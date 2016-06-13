package com.alexsophia.alexsophiautils.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.alexsophia.alexsophiautils.app.MyApplication;
import com.alexsophia.alexsophiautils.app.SPUserInfo;


/**
 * 网络访问相关的工具类
 * Created by Alexander on 2016/4/27.
 */
public class NetworkUtils {
    /**
     * 定义所有所需网络环境的类型
     */
    public enum NETWORK_STATUS {
        NETWORK_DISABLED, WIFI, CELLULAR, UNKNOWN
    }

    public static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");

    /**
     * 根据当前网络状态及WiFi开关判断当前是否允许下载
     *
     * @return true：允许下载；false：禁止下载
     */
    public static boolean isAllowDownload() {
        NetworkUtils.NETWORK_STATUS status = NetworkUtils.getNetworkType(MyApplication.getInstance());
        if (status == NetworkUtils.NETWORK_STATUS.NETWORK_DISABLED) {
            /**
             * 无网络或网络异常：禁止下载
             */
            return false;
        } else if (status == NetworkUtils.NETWORK_STATUS.WIFI) {
            /**
             * WiFi情况，不判定开关，直接允许下载
             */
            return true;
        } else {
            /**
             * 数据连接情况：
             * 如果打开只允许WiFi下载（isWiFiDownloadOnly == true），则禁止下载；
             * 如果没有打开只允许WiFi下载（isWiFiDownloadOnly == false），则允许下载；
             */
            SPUserInfo sp = new SPUserInfo(MyApplication.getInstance());
            return !sp.getIsWIFIDownloadOnly();
        }
    }

    public static NETWORK_STATUS getNetworkType(Context mContext) {
        try {
            final ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isAvailable()) {
                // 注意一：
                // NetworkInfo 为空或者不可以用的时候正常情况应该是当前没有可用网络，
                // 但是有些电信机器，仍可以正常联网，
                // 所以当成net网络处理依然尝试连接网络。
                // （然后在socket中捕捉异常，进行二次判断与用户提示）。
                return NETWORK_STATUS.NETWORK_DISABLED;
            } else {
                // NetworkInfo不为null开始判断是网络类型
                int netType = networkInfo.getType();
                if (netType == ConnectivityManager.TYPE_WIFI) {
                    // wifi net处理
                    return NETWORK_STATUS.WIFI;
                } else if (netType == ConnectivityManager.TYPE_MOBILE) {
                    return NETWORK_STATUS.CELLULAR;
                } else {
                    return NETWORK_STATUS.UNKNOWN;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return NETWORK_STATUS.UNKNOWN;
        }
    }
}
