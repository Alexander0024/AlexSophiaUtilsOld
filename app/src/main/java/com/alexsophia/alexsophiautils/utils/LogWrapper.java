package com.alexsophia.alexsophiautils.utils;


import android.util.Log;

import com.alexsophia.alexsophiautils.BuildConfig;


//Debug 模式 或 open 状态下输出log
public final class LogWrapper {
	private static boolean DEBUG = BuildConfig.DEBUG;
	private static boolean OPEN = false;

	public final static void setDebugMode(boolean debug) {
		DEBUG = debug;
	}

	public final static void openLog(boolean open) {
		OPEN = open;
	}

	/** info */
	public static void i(String tag, String msg) {
		if (DEBUG || OPEN)
			Log.i(tag, msg + "");
	}

	/** debug */
	public final static void d(String tag, String msg) {
		if (DEBUG || OPEN)
			Log.d(tag, msg + "");
	}

	/** error */
	public final static void e(String tag, String msg) {
		if (DEBUG || OPEN)
			Log.e(tag, msg + "");
	}

	/** warm */
	public final static void w(String tag, String msg) {
		if (DEBUG || OPEN)
			Log.w(tag, msg + "");
	}

	/** verbose */
	public final static void v(String tag, String msg) {
		if (DEBUG || OPEN)
			Log.v(tag, msg + "");
	}
}
