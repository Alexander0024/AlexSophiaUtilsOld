package com.alexsophia.alexsophiautils.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期帮助类，负责处理所有与日期有关的逻辑
 * 
 * @author liuweiping
 * @modify Alexander 2015-12-01 合并主线项目utils中原有的各种DateUtils类到此处
 * 
 */
@SuppressLint("SimpleDateFormat")
public final class DateUtil {
	/** 日期分隔符，短横线“-” */
	public static final String DATE_LINE = "-";

	/** 日期类型枚举类 */
	public static enum DateStrType {
		/** yyyy-MM-dd HH:mm */
		SIMPLE,
		/** yyyy.MM.dd HH:mm */
		SIMPLEDOT,
		/** yyyy-MM-dd HH:mm:ss */
		LONG,
		/** MM-dd HH:mm:ss */
		SHORT,
		/** yyyy-MM-dd */
		DAYONLY,
		/** yyyyMMdd */
		DAYSHORTONLY,
		/** HH:mm:ss */
		TIMEONLY,
		/** MM-dd */
		SHORTDAYONLY,
		/** HH:mm */
		SHORTTIMEONLY,
		/** yyyy.MM.dd.HH.mm.ss */
		ALLBYDOT,
		/** MM/dd HH:mm */
		PARENTS

	}

	/**
	 * 获取当前时间的毫秒数
	 */
	public final static long getCurrentMilliSeconds() {
		return System.currentTimeMillis();
	}

	/**
	 * 将毫秒数转换成Date对象
	 * 
	 * @param mills
	 *            毫秒数
	 * @return 生成的Date对象
	 */
	public final static Date getDateByMills(long mills) {
		if (0 <= mills) {
			return new Date();
		}
		return new Date(mills);
	}

	/**
	 * 将特定字符串转换成日期对象
	 * 
	 * @param dateStr
	 *            字符串类型的日期
	 * @return 日期的Date对象
	 */
	public final static Date getDateByStr(String dateStr) {
		if (null == dateStr || "".equals(dateStr)) {
			return null;
		}
		SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat
				.getDateInstance();
		try {
			return dateFormat.parse(dateStr);
		} catch (ParseException e) {
			return getDateByStr(dateStr, DATE_LINE);
		}
	}

	/**
	 * 将特定字符串转换成日期对象
	 * 
	 * @param dateStr
	 *            字符串类型的日期
	 * @param splitStr
	 *            日期的分隔符
	 * @return 日期的Date对象
	 */
	@SuppressWarnings("deprecation")
	public final static Date getDateByStr(String dateStr, String splitStr) {
		if (null == dateStr || "".equals(dateStr)) {
			return null;
		}
		String[] strs = dateStr.split(splitStr);
		if (strs.length != 3) {
			return null;
		}
		int year = 0;
		int month = 1;
		int day = 0;
		if (strs[0] != null && !"".equals(strs[0])) {
			year = Integer.parseInt(strs[0]);
		}
		if (strs[1] != null && !"".equals(strs[1])) {
			month = Integer.parseInt(strs[1]);
		}
		if (strs[2] != null && !"".equals(strs[2])) {
			day = Integer.parseInt(strs[2]);
		}
		return new Date(year, month - 1, day);
	}

	/**
	 * 将特定日期字符串转换成毫秒数
	 * 
	 * @param dateStr
	 *            String类型的日期对象
	 * @return long时间戳
	 */
	public final static long getMillsByStr(String dateStr) {
		SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat
				.getDateInstance();
		try {
			Date date = dateFormat.parse(dateStr);
			return date.getTime();
		} catch (ParseException e) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date = format.parse(dateStr);
				return date.getTime();
			} catch (ParseException e1) {
				return 0;
			}
		}
	}

	/**
	 * 根据基准日期获取前后某天的0点时间戳
	 * 
	 * @param dateStr
	 *            基准日期2015-10-10
	 * @param gapDays
	 *            距离基准日期的天数，往后为整数；往前为负数
	 * @return 获取基准日期前后某天的0点时间戳
	 */
	public final static long getMillsByStr(String dateStr, int gapDays) {
		long baseDay = getMillsByStr(dateStr);
		long oneDay = 24 * 60 * 60 * 1000;
		return baseDay + gapDays * oneDay;
	}

	/**
	 * 根据当前选择的格式参数获取当前日期时间的字符串
	 * 
	 * @param type
	 *            需要的String字符串格式类型
	 * @return
	 */
	public final static String getCurrentDateStr(DateStrType type) {
		return getDateStr(getCurrentMilliSeconds(), type);
	}

	/**
	 * 将毫秒数转换成Date字符串
	 * 
	 * @param mills
	 *            毫秒数
	 * @param pattern
	 *            转换成的字符串格式
	 * @return Date的字符串
	 */
	public final static String getDateStr(long mills, String pattern) {
		Date date = getDateByMills(mills);
		if (pattern == null) {
			return getDateStr(mills, DateStrType.LONG);
		}
		return getDateStr(date, pattern);
	}

	/**
	 * 将Date对象根据格式转换成Date字符串
	 * 
	 * @param date
	 *            日期对象
	 * @param pattern
	 *            转换成的字符串格式
	 * @return Date的字符串
	 */
	public final static String getDateStr(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 将毫秒数转换成Date字符串
	 * 
	 * @param mills
	 *            毫秒数
	 * @param type
	 *            DateStrType格式
	 * @return Date的字符串
	 */
	public final static String getDateStr(long mills, DateStrType type) {
		SimpleDateFormat dateFormat = null;
		if (type == DateStrType.DAYONLY) {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		} else if (type == DateStrType.DAYSHORTONLY) {
			dateFormat = new SimpleDateFormat("yyyyMMdd");
		} else if (type == DateStrType.LONG) {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else if (type == DateStrType.SIMPLE) {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		} else if (type == DateStrType.SIMPLEDOT) {
			dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		} else if (type == DateStrType.ALLBYDOT) {
			dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		} else if (type == DateStrType.SHORT) {
			dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
		} else if (type == DateStrType.TIMEONLY) {
			dateFormat = new SimpleDateFormat("HH:mm:ss");
		} else if (type == DateStrType.PARENTS) {
			dateFormat = new SimpleDateFormat("MM/dd HH:mm");
		} else if (type == DateStrType.SHORTDAYONLY) {
			dateFormat = new SimpleDateFormat("MM-dd");
		} else if (type == DateStrType.SHORTTIMEONLY) {
			dateFormat = new SimpleDateFormat("HH:mm");
		} else {
			dateFormat = (SimpleDateFormat) SimpleDateFormat.getInstance();
		}
		return dateFormat.format(new Date(mills));
	}

	/**
	 * 根据毫秒获取格式化的时间字符串
	 * 
	 * @param second
	 *            需要格式化的秒数
	 * @return 如果小于3600s（1h）则显示mm:ss格式，如果大于则显示HH:mm:ss格式
	 */
	public final static String getTimeStrBySecond(int second) {
		if (second < 0) {
			return null;
		} else if (second < 3600) {
			Date date = new Date(second * 1000);
			SimpleDateFormat sf = new SimpleDateFormat("mm:ss");
			return sf.format(date);
		} else {
			Date date = new Date(second * 1000);
			SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
			return sf.format(date);
		}
	}

	/**
	 * 根据字符串获取Calendar
	 * 
	 * @param dateStr
	 *            日期
	 * @return
	 */
	public final static Calendar getCalendar(String dateStr) {
		Calendar calendar = null;
		if (dateStr == null || dateStr.equals(""))
			return calendar;
		String[] strs = dateStr.split(DATE_LINE);
		calendar = Calendar.getInstance();
		if (strs == null || strs.length == 0)
			return calendar;
		if (strs[0] != null && !"".equals(strs[0])) {
			calendar.set(Calendar.YEAR, Integer.parseInt(strs[0]));
		}
		if (strs[1] != null && !"".equals(strs[1])) {
			calendar.set(Calendar.MONTH, Integer.parseInt(strs[1]) - 1);
		}
		if (strs[2] != null && !"".equals(strs[2])) {
			calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(strs[2]));
		}
		return calendar;
	}

	/**
	 * 根据日期判断两天之间的天数
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束如期
	 * @return 开日和结束日期之间的天数
	 */
	public final static int getDaysLengthBetweenDays(String startDate,
			String endDate) {
		int daysBetween = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + DATE_LINE + "MM"
				+ DATE_LINE + "dd");
		try {
			Date d1 = sdf.parse(startDate);
			Date d2 = sdf.parse(endDate);
			daysBetween = (int) ((d2.getTime() - d1.getTime() + 1000000) / (3600 * 24 * 1000));
		} catch (Exception e) {
		}
		return daysBetween + 1;
	}

	/**
	 * 判断给定字符串时间是否为今日
	 * 
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		boolean b = false;
		Date time = getDateByStr(sdate);
		Date today = new Date();
		if (time != null) {
			String nowDate = sdf.format(today);
			String timeDate = sdf.format(time);
			if (nowDate.equals(timeDate)) {
				b = true;
			}
		}
		return b;
	}

	/**
	 * 将日期转换成友好日期
	 * 
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date time = getDateByStr(sdate);
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// 判断是否是同一天
		String curDate = sdf.format(cal.getTime());
		String paramDate = sdf.format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
		} else if (days == 1) {
			ftime = "昨天";
		} else if (days == 2) {
			ftime = "前天";
		} else if (days > 2 && days <= 10) {
			ftime = days + "天前";
		} else if (days > 10) {
			ftime = sdf.format(time);
		}
		return ftime;
	}

	/**
	 * 将时间转换为剩余时间
	 */
	public static String endTime(long expirationDate) {
		long currTime = getCurrentMilliSeconds();
		long diff = expirationDate - currTime;
		long days = diff / (1000 * 60 * 60 * 24);
		long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
				* (1000 * 60 * 60))
				/ (1000 * 60);
		if (days > 0) {
			return "剩余" + days + "天" + hours + "小时";
		} else {
			return "剩余" + hours + "小时" + minutes + "分钟";
		}
	}

	/**
	 * 今天显示时间，昨天显示昨天，昨天以前显示日期
	 */
	public static String parentData(long publishData) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd");
		Date time = new Date(publishData);
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();
		// 判断是否是同一天
		String curDate = sdf.format(cal.getTime());
		String paramDate = sdf.format(time);
		if (curDate.equals(paramDate)) {
			ftime = sdf.format(time);
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			ftime = sdf.format(time);
		} else if (days == 1) {
			ftime = "昨天";
		} else {
			ftime = sdf1.format(time);
		}
		return ftime;
	}

	/**
	 * 根据时间显示不同的时间类型
	 *
	 * @param timestamp 时间戳
	 * @return <br>今天的话显示今天的时间，如：12:00</br>
	 * <br>非今天的话显示日期加时间</br>
	 */
	public static String displayFlexibleDate(long timestamp) {
		if (timestamp > getTimeMorning()) {
			// today
			return getDateStr(timestamp, DateStrType.SHORTTIMEONLY);
		}
		return getDateStr(timestamp, DateStrType.PARENTS);
	}

	/**
	 * 获取今天0点的时间戳
	 * @return
	 */
	public static long getTimeMorning(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	/**
	 * 获取昨天0点的时间戳
	 * @return
	 */
	public static long getTimeYesterdayMorning(){
		return getTimeMorning() - 24 * 60 * 60 * 1000;
	}

	/**
	 * 判断是否在两小时之内
	 * @param expiration_date
	 * @return
	 */
	public static boolean isTimeTopic(long expiration_date) {
		long twoTime = (1000 * 60 * 60 * 24 * 2);
		long difTime = getCurrentMilliSeconds() - expiration_date;
		if (difTime > 0 && difTime < twoTime) {// 不过期且处于两小时之内
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 时间再1到时间之间显示0？
	 * @param i
	 * @return
	 */
	private static String unitFormat(int i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Integer.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}

	/**
	 * 将毫秒转为？小时？分
	 * @param time
	 * @return
	 */
	public static String longToTimeS(int time) {
		String timeStr = null;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if (time <= 0)
			return "00分00秒";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = unitFormat(minute) + "分" + unitFormat(second)+"秒";
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99时59分59秒";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + "时" + unitFormat(minute) + "分"
						+ unitFormat(second)+"秒";
			}
		}
		return timeStr;
	}

}
