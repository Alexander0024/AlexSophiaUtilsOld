package com.alexsophia.alexsophiautils.util.json;

import android.util.Log;

/**
 * Json打印类
 * 可以打印超长json，但是会占用大量Logcat屏幕
 * 
 * 调用方法：JsonFormatUtil.printJson(JsonFormatUtil.LOG_LEVEL.ERROR, tag, json);
 * 
 * @author Alexander
 *
 */
public class JsonFormatUtil {
	// 单位缩进字符串。
	private static final String SPACE = "   ";
	// 后括号，用于分割超长json内容
	private static final String SPLIT_SPACE = "}";
	// 每批次打印的内容范围
	private static final int STEP = 3500;
	// Logcat可以安全打印的长度
	private static final int MAX_LENGTH = 4000;
	/**
	 * 需要打印的类型
	 * Info: i
	 * Debug: d
	 * Warning: w
	 * Error: e
	 * Verbose: v
	 * @author Alexander
	 *
	 */
	public static enum LOG_LEVEL {INFO, DEBUG, WARNING, ERROR, VERBOSE};

	/**
	 * 调用打印Json，如果内容小于{@code MAX_LENGTH}则一次性打印，如果超过则分批打印
	 * @param level 需要打印到Logcat的level，参照{@code LOG_LEVEL}
	 * @param tag 打印的标签Tag
	 * @param json 需要打印的完整Json
	 */
	public static void printJson(LOG_LEVEL level, String tag, String json) {
		if (json.startsWith("{") && json.endsWith("}")) {
			Log.e(tag, "↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓");
			int json_length, index_start = 0, index_end = STEP;
			String formatedJson = formatJson(json);
			json_length = formatedJson.length();
			if (json_length < MAX_LENGTH) {
				// 长度在Logcat可打印范围之内，直接打印
				print(level, tag, formatedJson);
			} else {
				// 长度大于Logcat可打印的警戒值，分批次打印
				while (index_end < json_length) {
					// 找到STEP位置后的第一个后括号
					index_end = formatedJson.indexOf(SPLIT_SPACE, index_end);
					if (index_end == -1) {
						// 错误处理，几乎不会走，json以}结尾
						break;
					}
					// 向后获取真正分段的具体index，如为"},"则加2，为"}"则加1
					if (",".equals(formatedJson.charAt(index_end + 1) + "")) {
						index_end += 2;
					} else {
						index_end += 1;
					}
					print(level, tag, formatedJson.substring(index_start, index_end));
					index_start = index_end;
					index_end += STEP;
				}
				print(level, tag, formatedJson.substring(index_start));
			}
		} else {
			print(level, tag, json);
		}
	}
	
	/**
	 * 执行打印
	 * @param level
	 * @param tag
	 * @param msg
	 */
	private static void print(LOG_LEVEL level, String tag, String msg) {
		switch (level) {
		case INFO:
			Log.i(tag, msg);
			break;
		case DEBUG:
			Log.d(tag, msg);
			break;
		case WARNING:
			Log.w(tag, msg);
			break;
		case ERROR:
			Log.e(tag, msg);
			break;
		case VERBOSE:
			Log.v(tag, msg);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 返回格式化JSON字符串。
	 * 
	 * @param json
	 *            未格式化的JSON字符串。
	 * @return 格式化的JSON字符串。
	 */
	private static String formatJson(String json)
	{
		StringBuffer result = new StringBuffer();
		int length = json.length();
		int number = 0;
		char key = 0;
		// 遍历输入字符串。
		for (int i = 0; i < length; i++)
		{
			// 1、获取当前字符。
			key = json.charAt(i);
			// 2、如果当前字符是前方括号、前花括号做如下处理：
			if ((key == '[') || (key == '{'))
			{
				// （1）如果前面还有字符，并且字符为“：”，打印：换行和缩进字符字符串。
				if ((i - 1 > 0) && (json.charAt(i - 1) == ':'))
				{
					result.append('\n');
					result.append(indent(number));
				}
				// （2）打印：当前字符。
				result.append(key);
				// （3）前方括号、前花括号，的后面必须换行。打印：换行。
				result.append('\n');
				// （4）每出现一次前方括号、前花括号；缩进次数增加一次。打印：新行缩进。
				number++;
				result.append(indent(number));
				// （5）进行下一次循环。
				continue;
			}
			// 3、如果当前字符是后方括号、后花括号做如下处理：
			if ((key == ']') || (key == '}'))
			{
				// （1）后方括号、后花括号，的前面必须换行。打印：换行。
				result.append('\n');
				// （2）每出现一次后方括号、后花括号；缩进次数减少一次。打印：缩进。
				number--;
				result.append(indent(number));
				// （3）打印：当前字符。
				result.append(key);
				// （4）如果当前字符后面还有字符，并且字符不为“，”，打印：换行。
				if (((i + 1) < length) && (json.charAt(i + 1) != ','))
				{
					result.append('\n');
				}
				// （5）继续下一次循环。
				continue;
			}
			// 4、如果当前字符是逗号。逗号后面换行，并缩进，不改变缩进次数。
			if ((key == ','))
			{
				result.append(key);
				result.append('\n');
				result.append(indent(number));
				continue;
			}
			// 5、打印：当前字符。
			result.append(key);
		}
		return result.toString();
	}

	/**
	 * 返回指定次数的缩进字符串。每一次缩进三个空格，即SPACE。
	 * 
	 * @param number
	 *            缩进次数。
	 * @return 指定缩进次数的字符串。
	 */
	private static String indent(int number)
	{
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < number; i++)
		{
			result.append(SPACE);
		}
		return result.toString();
	}
}
