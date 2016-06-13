package com.alexsophia.alexsophiautils.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

class LogUtils {
	// ======================================= Logcat Helper Information
	// 用于记录日常日志的路径
	private static String DEFAULT_LOG_FILE_PATH = Environment
		.getExternalStorageDirectory().toString() + "/liren/LogReport/";

	// 用于记录日志的文件名
	private static String DEFAULT_LOG_FILE_NAME = "-current-use.log";

	// 获取当前系统日志文件名
	public static String getLogcatHelperPath(String packageName,
			String logFilePath, String logFileName) {
		if (null == logFilePath || "".equals(logFilePath)) {
			logFilePath = DEFAULT_LOG_FILE_PATH;
		}
		if (null == logFileName || "".equals(logFileName)) {
			logFileName = DEFAULT_LOG_FILE_NAME;
		}
		return logFilePath
				+ packageName.substring(packageName.lastIndexOf('.') + 1)
				+ logFileName;
	}

	public static String getLineTime() {
		return formatter_lineTime.format(new Date(System.currentTimeMillis()));
	}

	@SuppressLint("SimpleDateFormat")
	private static DateFormat formatter_lineTime = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss");

	public static String getDefaultLogFilePath() {
		return DEFAULT_LOG_FILE_PATH;
	}

	public static String getDefaultLogFileName() {
		return DEFAULT_LOG_FILE_NAME;
	}

	// ======================================= Crash Handler Information
	// 获取当前崩溃日志文件名
	public static String getDefaultCrashFileName(Context context) {
		return "crash--"
				+ getCurrentTime()
				+ "--"
				+ context.getPackageName().substring(
					context.getPackageName().lastIndexOf(".") + 1) + ".log";
	}

	// 记录日志文件的文件数目
	public static int getDefaultCrashFileSize() {
		return 20;
	}

	private static String getCurrentTime() {
		return formatter_fileName.format(new Date(System.currentTimeMillis()))
				+ "--" + System.currentTimeMillis();
	}

	// 用于格式化日期,作为日志文件名的一部分
	@SuppressLint("SimpleDateFormat")
	private static DateFormat formatter_fileName = new SimpleDateFormat(
		"yyyy-MM-dd-HH-mm-ss");
}