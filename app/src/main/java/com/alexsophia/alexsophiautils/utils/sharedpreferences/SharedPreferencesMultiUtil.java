package com.alexsophia.alexsophiautils.utils.sharedpreferences;

import android.content.Context;

/**
 * 多进程SharedPreferences 工具类
 */
public class SharedPreferencesMultiUtil extends SharedPreferencesUtil {

    public SharedPreferencesMultiUtil(Context context, String name) {
        super(context, name, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
    }

    public SharedPreferencesMultiUtil(Context context, String name, int access) {
        super(context, name, access | Context.MODE_MULTI_PROCESS);
    }
}
