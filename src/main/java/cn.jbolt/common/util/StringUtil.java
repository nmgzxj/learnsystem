package cn.jbolt.common.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author Michael
 *
 */
public class StringUtil {
	public static String unescapeJava(String str) throws IOException {
		Writer out = new StringWriter();
		if (str != null) {
			int sz = str.length();
			StringBuilder unicode = new StringBuilder(4);
			boolean hadSlash = false;
			boolean inUnicode = false;

			for (int i = 0; i < sz; ++i) {
				char ch = str.charAt(i);
				if (inUnicode) {
					unicode.append(ch);
					if (unicode.length() == 4) {
						try {
							int nfe = Integer.parseInt(unicode.toString(), 16);
							out.write((char) nfe);
							unicode.setLength(0);
							inUnicode = false;
							hadSlash = false;
						} catch (NumberFormatException var9) {
						}
					}
				} else if (hadSlash) {
					hadSlash = false;
					switch (ch) {
					case '\"':
						out.write(34);
						break;
					case '\'':
						out.write(39);
						break;
					case '\\':
						out.write(92);
						break;
					case 'b':
						out.write(8);
						break;
					case 'f':
						out.write(12);
						break;
					case 'n':
						out.write(10);
						break;
					case 'r':
						out.write(13);
						break;
					case 't':
						out.write(9);
						break;
					case 'u':
						inUnicode = true;
						break;
					default:
						out.write(ch);
					}
				} else if (ch == 92) {
					hadSlash = true;
				} else {
					out.write(ch);
				}
			}

			if (hadSlash) {
				out.write(92);
			}

		}
		return out.toString();
	}

	public static boolean isNumeric(String str) {
		if (isBlank(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 首字母小写
	 * 
	 * @param s
	 * @return
	 */
	public static String toLowerCaseFirstWord(String s) {
		if (isBlank(s)) {
			return s;
		}
		if (Character.isLowerCase(s.charAt(0))) {
			return s;
		}
		if (s.length() == 1) {
			return Character.toLowerCase(s.charAt(0)) + "";
		}
		return Character.toLowerCase(s.charAt(0)) + s.substring(1);
	}

	/**
	 * 首字母大写
	 * 
	 * @param s
	 * @return
	 */
	public static String toUpperCaseFirstWord(String s) {
		if (isBlank(s)) {
			return s;
		}
		if (Character.isUpperCase(s.charAt(0))) {
			return s;
		}
		if (s.length() == 1) {
			return Character.toUpperCase(s.charAt(0)) + "";
		}
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}
	
	public static String toUpperCase(String str){
		if(isNotBlank(str)){
			return str.toUpperCase();
		}
		return str;
	}

	/**
	 * 判断空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isBlank(String s) {
		if (s == null || s.trim().equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 判断不是空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNotBlank(String s) {
		return !isBlank(s);
	}

	/**
	 * 判断空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (s == null || s.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 判断不是空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

	/**
	 * 判断存在多少个指定字符
	 * 
	 * @param s
	 * @param c
	 * @return
	 */
	public static int count(String s, char c) {
		if (s == null) {
			return 0;
		}
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			if (c == s.charAt(i)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 数组转字符串
	 * 
	 * @param arrays
	 * @return
	 */
	public static String toString(String[] arrays) {
		if (arrays == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (String array : arrays) {
			sb.append(array);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * 替换
	 * 
	 * @param str
	 * @param from
	 * @param to
	 * @return
	 */
	public static String replace(String str, String from, String to) {
		if (str == null) {
			return null;
		}
		return str.replaceAll(from, to);
	}

	/**
	 * list转字符串
	 * 
	 * @param list
	 * @param separator
	 * @return
	 */
	public static String listToString(List list, String separator) {
		if (list == null || list.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null) {
				continue;
			}
			sb.append(list.get(i)).append(separator);
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}

	/**
	 * array转字符串
	 * 
	 * @param list
	 * @param separator
	 * @return
	 */
	public static String arrayToString(String[] args, String separator) {
		if (args == null || args.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			if (args[i] == null) {
				continue;
			}
			sb.append(args[i]).append(separator);
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}

	/**
	 * 获得字符串的前多少字
	 * 
	 * @return
	 */
	public static String getPreWords(String str, int count, String omittedWords) {
		String newStr;
		if (str == null) {
			return null;
		}
		if (str.length() <= count) {
			return str;
		}
		newStr = str.substring(0, count);
		if (omittedWords != null) {
			return newStr + omittedWords;
		}
		return newStr;
	}

	public static String filterEmoji(String source) {
		if (source != null && source.length() > 0) {
			return source.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "");
		} else {
			return source;
		}
	}

}
