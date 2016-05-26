package com.alexsophia.alexsophiautils.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串帮助类
 *
 * @author liuweiping
 *
 */
public final class StringUtil {
	// 特殊字符替代符
	public static final String REPLACE_STR = "";
	/**
	 * 按照特定字符截取字符串
	 *
	 * @param str
	 *            被截取的字符串
	 * @param op
	 *            截取字符
	 * @return 返回截取
	 */
	public final static String[] getStrArray(String str, String op) {
		return str.split(op);
	}

	/**
	 * 找出特定字符在某个字符串中出现的所有位置的集合
	 */

	public final static List<Integer> getStrPosition(String str, String op) {
		List<Integer> pos = null;
		if (str != null && op != null) {
			if (!str.equals("") && !op.equals("")) {
				pos = new ArrayList<>();
				int start = 0;
				while (start != str.length()) {
					int i = str.indexOf(op, start);
					if (i >= 0) {
						start = i + 1;
						pos.add(i);
					} else
						break;
				}
			}
		}
		return pos;

	}

	/**
	 * 判断一个字符串是否全是数字
	 */
	public static boolean isAllNumber(String str) {
//		return match(str, "^[0-9]+$?");
		return match(str,"^\\d+$");
	}

	/**
	 * 是否全为字母
	 */
	public static boolean isChar(String str) {
		return match(str, "^[a-zA-Z]+$");
	}
	/**
	 * 是否为汉字
	 *
	 * @param str
	 * @return
	 */
	public static boolean isChineseCharacters(String str) {
		return match(str, "^[\u4e00-\u9fa5]+$");
	}
	/**
	 * 判断一个字符串是否为数字和字母
	 */
	public static boolean isNumberOrChar(String str) {
		return match(str, "^[0-9a-zA-Z]+$");
	}

	/**
	 * 判断一个字符串是否为邮箱地址
	 */
	public static boolean isEmail(String str) {
		// String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		return match(str,
				"^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
	}

	/**
	 * 是否为手机号码
	 */
	public static boolean isPhoneNumber(String str) {
		return match(str,
                "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$");
	}

    /**
     * 隐藏手机号码中的中间4位
     *
     * @param phone 11位手机号码
     * @return 137****6789
     */
    public static String hidePhone(String phone) {
        if (isEmpty(phone)) {
            return "";
        } else if (phone.length() == 11) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(phone.substring(0, 3));
            stringBuilder.append("****");
            stringBuilder.append(phone.substring(7));
            return stringBuilder.toString();
        } else {
            return phone;
        }
    }

	/**
	 * 隐藏邮箱中间的位数
	 * @param mail 邮箱地址
	 * @return 隐藏邮箱地址
	 */
	public static String hideEmail(String mail) {
		StringBuilder stringBuilder = new StringBuilder();
		if (isEmpty(mail) || !isEmail(mail)) {
			return "";
		} else if (mail.lastIndexOf("@") > 0) {
			int index = mail.lastIndexOf("@");
			if (index > 12) {
				stringBuilder.append(mail.substring(0, 3));
				stringBuilder.append("***");
				stringBuilder.append(mail.substring(index - 3));
			} else {
				stringBuilder.append(mail.substring(0, 1));
				stringBuilder.append("***");
				stringBuilder.append(mail.substring(index - 1));
			}
		}
		return stringBuilder.toString();
	}

    /**
	 * 是否为6-16位字符串、空格或者英文标点
	 */
	public static boolean isPassword(String str) {
		return match(str, "[\\w\\s~`!@#$%^&*( )_+{ }|:”< >?/.,’;= -]{6,16}") && isNotContainsChinese(str);
	}

	/**
	 * 过滤输入中的中文字符
	 * @param str 输入的string字符串
	 * @return true：if not contains chinese code; false otherwise.
     */
	private static boolean isNotContainsChinese(String str) {
		for (int i = 0; i < str.length(); i ++) {
			char c = str.charAt(i);
			if (isChinese(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 *  \w：用于匹配字母，数字或下划线字符
	 *  \d：用于匹配从0到9的数字；
	 *  \/ ：表示字符"/"。
	 *  "^"开头
	 *  "$"结尾
	 *  "*"，"+"和"?"这三个符号，表示一个或一序列字符重复出现的次数。它们分别表示“没有或
				更多”，“一次或更多”还有“没有或一次”。
	 * @param str
	 * @param rex
	 * @return
	 */
	public static boolean match(String str, String rex) {
		if (null == str || str.trim().length() == 0) {
			return false;
		}
		Pattern pattern = Pattern.compile(rex);
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 *
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 过滤输入key中的非法字符：清除掉所有特殊字符
	 *
	 * @param key
	 *            用户输入的原始key
	 * @return 过滤后的字符串
	 */
	public static String stringFilter(String key) {
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(key);
		return m.replaceAll("").trim();
	}

//	/**
//	 * 将特殊的符号变成*
//	 * @param source
//	 * @return
//	 */
//	public static String filterEmoji(String source) {
//		if (source != null && !"".equals(source)) {
//			return source.replaceAll(
//				"[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", REPLACE_STR);
//		} else {
//			return source;
//		}
//	}

	/**
	 * 判断输入内容中是否包含Emoji符号
	 * @param source 输入的内容
	 * @return true：包含Emjoi；false：不包含Emoji
     */
	public static boolean isContainsEmoji(String source) {
		if (isEmpty(source)) {
			return false;
		}
		int length = source.length();
		for (int i = 0; i < length; i++) {
			char code = source.charAt(i);
			if (isEmoji(code)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isEmoji(char codePoint) {
		return !((codePoint == 0x0) ||
				(codePoint == 0x9) ||
				(codePoint == 0xA) ||
				(codePoint == 0xD) ||
				((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
				((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
				((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
	}

	/**
	 * 判断str中有几个substring
	 *
	 * @param str
	 * @param substring
	 * @return
	 */
	public static int substringCount(String str, String substring) {
		if (str.contains(substring)) {
			String strReplaced = str.replace(substring, "");
			return (str.length() - strReplaced.length()) / substring.length();
		}
		return 0;
	}

	/**
	 * 字符串转整数
	 *
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 对象转整数
	 *
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	/**
	 * 对象转整数
	 *
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * 字符串转布尔值
	 *
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 针对TextView显示中文中出现的排版错乱问题，通过调用此方法得以解决
	 *
	 * @param input
	 * @return 返回全部为全角字符的字符串
	 */
	 public static String toSBC(String input) {
		  // 半角转全角：
		  char[] c = input.toCharArray();
		  for (int i = 0; i < c.length; i++) {
		   if (c[i] == 32) {
		    c[i] = (char) 12288;
		    continue;
		   }
		   if (c[i] < 127 && c[i]>32)
		    c[i] = (char) (c[i] + 65248);
		  }
		  return new String(c);
		 }

	/**
	 * 针对TextView显示中文中出现的排版错乱问题，通过调用此方法得以解决
	 *
	 * @param input
	 * @return 返回全部为半角字符的字符串
	 */
	public static String toDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (isChinese(c[i])) {
				if (c[i] == 12288) {
					c[i] = (char) 32;
					continue;
				}
				if (c[i] > 65280 && c[i] < 65375)
					c[i] = (char) (c[i] - 65248);
			}
		}
		return new String(c);
	}

	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}
	/*
	 * 格式化包含图片的文本(带前缀),如: prefix: http://pre
	 * {0}，{1}的dfs@{/upload/79923.jpg,/uplo/4924.jpg,} ==>> <img
	 * src="http://pre/upload/79923.jpg">，<img
	 * src="http://pre/uplo/4924.jpg">的dfs
	 */
	public static String formatImgtext(String domain, String text) {
		if (text == null || "".equals(text)) {
			return "";
		}
		String[] separate = text.split("@\\{");
		if (separate.length != 2 || separate[1].length() < 2
				|| separate[1].charAt(separate[1].length() - 1) != '}') {
			return formatText(text,domain);
		}
		separate[1] = separate[1].substring(0, separate[1].length() - 1);
		String[] imgArr = separate[1].split(",");
		if (imgArr.length < 0) {
			return formatText(text,domain);
		}
		for (int i = 0; i < imgArr.length; i++) {
			separate[0] = separate[0].replace("{" + i + "}", "<img src =\\\""
					+imgArr[i] + "\\\">");

		}
		return formatText(separate[0],domain);

	}

	/*
	 * 格式化包含图片的文本(带前缀),如: prefix: http://pre
	 * {0}，{1}的dfs@{/upload/79923.jpg,/uplo/4924.jpg,} ==>> <img
	 * src="http://pre/upload/79923.jpg">，<img
	 * src="http://pre/uplo/4924.jpg">的dfs
	 */
	public static String formatImgtext1(String prefix, String text) {
		if (text == null || "".equals(text))
			return text;
		String[] separate = text.split("@\\{");
		if (separate.length != 2 || separate[1].length() < 2
				|| separate[1].charAt(separate[1].length() - 1) != '}')
			return text;

		separate[1] = separate[1].substring(0, separate[1].length() - 1);
		String[] imgArr = separate[1].split(",");
		if (imgArr.length < 0)
			return text;

		LogWrapper.e("ddd", "dxf: " + separate[1] + "; arr: " + imgArr.length);
		for (int i = 0; i < imgArr.length; i++) {
			separate[0] = separate[0].replace("{" + i + "}", "<img src =\""
					+ prefix + imgArr[i] + "\">");
		}
		return separate[0];
	}
	/**
	 * 转义单引号，/</n><//n>
	 */
	public static String formatText(String text,String domain){
		if (text == null || "".equals(text)) {
			return text;
		}
		text = text.replaceAll("\"","\'").replaceAll("\t", " ").replaceAll("\r","<br>").replaceAll("/upload",domain+"/upload");
		return  text;
	}

	/**
	 * 格式化错题解析中填空题和问答题多个答案时的“我的答案”的展示效果
	 * @param text
	 * @return
	 */
	public static String formatListText(String text) {

		String[] separate = text.split(Constant.SEPANSWER);
		if (1 == separate.length) {
			return text;
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < separate.length; i++) {
			int num = i + 1;
			stringBuilder.append(num + "#*"
					+ separate[i] + "#*");
		}
		String s = stringBuilder.toString();
		return s.substring(0, s.length() - 2);
	}
	/**
	 * 获取网络下载地址 由domain 和path 组成
	 * 如果是以http://开头，则直接返回；否则用domain和path拼接而成
	 */
	public static String getImageNetPath(String path, String domain) {
		if (path.startsWith("http://")) {
			return path;
		} else {
			if (domain.endsWith("/") && path.startsWith("/")) {
				return domain + path.substring(1);
			} else if (!domain.endsWith("/") && path.startsWith("/")) {
				return domain + path;
			} else if (domain.endsWith("/") && !path.startsWith("/")) {
				return domain + path;
			} else {
				return domain + "/" + path;
			}
		}
	}
	//格式化 电子化移交完成率 保留两位
	public static String formateRate(String rateStr){
		if(rateStr.indexOf(".") != -1){
			//获取小数点的位置
			int num = 0;
			num = rateStr.indexOf(".");

			//获取小数点后面的数字 是否有两位 不足两位补足两位
			String dianAfter = rateStr.substring(0,num+1);
			String afterData = rateStr.replace(dianAfter, "");
			if(afterData.length() < 2){
				afterData = afterData + "0" ;
			}else{
				afterData = afterData;
			}
			return rateStr.substring(0,num) + "." + afterData.substring(0,2);
		}else{
			if(rateStr == "1"){
				return "100";
			}else{
				return rateStr;
			}
		}
	}

}
