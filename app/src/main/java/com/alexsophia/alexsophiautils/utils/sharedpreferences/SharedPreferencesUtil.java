package com.alexsophia.alexsophiautils.utils.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.alexsophia.alexsophiautils.utils.LogWrapper;

import java.util.Map;

/**
 * SharedPreferences 工具类
 * <p/>
 * Copied from Student:LRE-Bag Project
 *
 * @author liqian
 */
public abstract class SharedPreferencesUtil {
    private final static String TAG = "SharedPreferencesUtil";

    private SharedPreferences sp = null;
    private final static String SP_VERSION = "SpVersion";
    protected int spVersion = 0;
    private String spName;

    // 获取SharedPreferences当前版本
    public int getCurSpVersion() {
        return 0;
    }

    // 低版本兼容处理
    protected void onCompatibleLow(int oldVersion) {

    }

    // 高版本版本兼容处理
    protected void onCompatibleHigh(int oldVersion) {

    }

    public SharedPreferencesUtil(Context context, String name) {
        this(context, name, Context.MODE_PRIVATE);
    }

    public SharedPreferencesUtil(Context context, String name, int access) {
        sp = context.getSharedPreferences(name, access);
        spName = name;
        initSp();
    }

    private void initSp() {
        int cur = getCurSpVersion();
        spVersion = sp.getInt(SP_VERSION, cur);
        onCompatible(spVersion, cur);
    }

    // 版本兼容处理
    protected void onCompatible(int oldVersion, int curVersion) {
        // 相同版本
        if (curVersion == oldVersion)
            return;

        // 版本有升级
        if (curVersion > oldVersion) {
            onCompatibleLow(oldVersion);
            sp.edit().putInt(SP_VERSION, getCurSpVersion()).commit();
            return;
        }

        // 版本出现降级，绝大多数时这是异常情况
        if (curVersion < oldVersion) {
            LogWrapper.e(TAG, spName + " :The VERSION has expired!! cur: "
                    + curVersion + "; before: " + oldVersion);
            onCompatibleHigh(oldVersion);
            return;
        }
    }

    public boolean contains(String key) {
        return sp.contains(key);
    }

    public boolean remove(String key) {
        LogWrapper.d(TAG, "remove " + key);
        return sp.edit().remove(key).commit();
    }

    public Map<String, ?> getString() {
        return sp.getAll();
    }

    // 将原有key中的值从String转成Int
    public boolean string2Int(String key) throws NumberFormatException {
        String val = sp.getString(key, "0");
        return sp.edit().putInt(key, Integer.parseInt(val)).commit();

    }

    // 将原有key中的值Int从转成String
    public boolean int2String(String key) {
        int val = sp.getInt(key, 0);
        return sp.edit().putString(key, "" + val).commit();

    }

    public boolean putByteArray(String key, byte[] input) {
        String productBase64 = new String(Base64.encode(input, Base64.DEFAULT));
        return putString(key, productBase64);
    }

    public byte[] getByteArray(String key) {
        if (sp.contains(key)) {
            String objectVal = sp.getString(key, null);
            return Base64.decode(objectVal, Base64.DEFAULT);
        }
        return null;
    }

    protected String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    protected String getString(String key) {
        return sp.getString(key, "");
    }

    protected boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    protected boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    protected int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    protected int getInt(String key) {
        return sp.getInt(key, 0);
    }

    protected long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    protected long getLong(String key) {
        return sp.getLong(key, 0);
    }

    protected float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    protected float getFloat(String key) {
        return sp.getFloat(key, 0);
    }

    protected boolean putString(String key, String value) {
        LogWrapper.d(TAG, "set " + key + " = " + value);
        return sp.edit().putString(key, value).commit();
    }

    protected boolean putBoolean(String key, boolean value) {
        LogWrapper.d(TAG, "set " + key + " = " + value);
        return sp.edit().putBoolean(key, value).commit();
    }

    protected boolean putInt(String key, int value) {
        LogWrapper.d(TAG, "set " + key + " = " + value);
        return sp.edit().putInt(key, value).commit();
    }

    protected boolean putLong(String key, long value) {
        LogWrapper.d(TAG, "set " + key + " = " + value);
        return sp.edit().putLong(key, value).commit();
    }

    protected boolean putFloat(String key, float value) {
        LogWrapper.d(TAG, "set " + key + " = " + value);
        return sp.edit().putFloat(key, value).commit();
    }
}
