package com.shanlin.framework.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class StringHelper {
	private static final char[] HEXES = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static final Pattern REPLACE_VARIABLE_PATTERN = Pattern.compile("\\$\\{\\s*(\\w|\\.|-|_|\\$)+\\s*\\}", 2);
	public static final String EMPTY_STRING = "";
	private static final byte[] ROW_BYTES = "80e36e39f34e678c".getBytes();

	public static String trim(String value) {
		return value == null ? null : value.trim();
	}

	public static boolean isEmpty(String value) {
		int length;
		if ((value == null) || ((length = value.length()) == 0)) {
			return true;
		}
		for (int index = 0; index < length; index++) {
			char ch = value.charAt(index);
			if ((ch != ' ') && (ch != ' ') && (!Character.isISOControl(ch))) {
				return false;
			}
		}
		return true;
	}

	public static void filterHTML(Appendable writer, String str) throws IOException {
		if (isEmpty(str)) {
			return;
		}
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (!Character.isISOControl(ch)) {
				switch (ch) {
				case '"':
				case '&':
				case '\'':
				case '<':
				case '>':
					writer.append("&#");
					writer.append(Integer.toString(ch));
					writer.append(';');
					break;
				default:
					writer.append(ch);
				}
			}
		}
	}

	public static void filterQuoter(Appendable writer, String str) throws IOException {
		if (isEmpty(str)) {
			return;
		}
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch == '"') {
				writer.append('\\');
			}
			writer.append(ch);
		}
	}

	public static void filterSingleQuoter(Appendable writer, String str) throws IOException {
		if (isEmpty(str)) {
			return;
		}
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch == '\'') {
				writer.append('\\');
			}
			writer.append(ch);
		}
	}

	public static String digest(String content) throws Throwable {
		if (isEmpty(content)) {
			return content;
		}
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		byte[] ciphertext = digest.digest(content.getBytes());
		char[] chars = new char[ciphertext.length + ciphertext.length];
		int i = 0;
		for (byte element : ciphertext) {
			chars[(i++)] = HEXES[(element & 0xF)];
			chars[(i++)] = HEXES[(element >> 4 & 0xF)];
		}
		return new String(chars);
	}

	public static String encode(String content) throws Throwable {
		if (isEmpty(content)) {
			return content;
		}
		Cipher cipher = Cipher.getInstance("AES");
		SecretKeySpec keySpec = new SecretKeySpec(ROW_BYTES, "AES");
		cipher.init(1, keySpec);
		byte[] ciphertext = cipher.doFinal(content.getBytes());
		return Base64.encodeBase64String(ciphertext);
	}

	public static String decode(String content) throws Throwable {
		if (isEmpty(content)) {
			return content;
		}
		Cipher cipher = Cipher.getInstance("AES");
		SecretKeySpec keySpec = new SecretKeySpec(ROW_BYTES, "AES");
		cipher.init(2, keySpec);
		byte[] ciphertext = cipher.doFinal(Base64.decodeBase64(content));
		return new String(ciphertext);
	}

	public static String truncation(String string, int maxLength) {
		if (isEmpty(string)) {
			return "";
		}
		try {
			StringBuilder out = new StringBuilder();
			truncation(out, string, maxLength, null);
			return out.toString();
		} catch (IOException e) {
		}
		return "";
	}

	public static String truncation(String string, int maxLength, String replace) {
		if (isEmpty(string)) {
			return "";
		}
		try {
			StringBuilder out = new StringBuilder();
			truncation(out, string, maxLength, replace);
			return out.toString();
		} catch (IOException e) {
		}
		return "";
	}

	public static void truncation(Appendable out, String string, int maxLength) throws IOException {
		truncation(out, string, maxLength, null);
	}

	public static void truncation(Appendable out, String string, int maxLength, String replace) throws IOException {
		if ((isEmpty(string)) || (maxLength <= 0)) {
			return;
		}
		if (isEmpty(replace)) {
			replace = "...";
		}
		int index = 0;
		int end = Math.min(string.length(), maxLength);
		for (; index < end; index++) {
			out.append(string.charAt(index));
		}
		if (string.length() > maxLength) {
			out.append(replace);
		}
	}

	public static void format(Appendable out, String pattern) throws IOException {
		if ((out == null) || (isEmpty(pattern))) {
			return;
		}
		Set<String> loadedKeys = new HashSet<>();
		format(out, pattern, loadedKeys);
	}

	private static final void format(Appendable out, String pattern, Set<String> loadedKeys) throws IOException {
		Matcher matcher = REPLACE_VARIABLE_PATTERN.matcher(pattern);
		int startIndex = 0;
		int endIndex = 0;
		while (matcher.find()) {
			endIndex = matcher.start();
			if (endIndex != startIndex) {
				out.append(pattern, startIndex, endIndex);
			}
			String key = matcher.group();
			key = key.substring(2, key.length() - 1).trim();
			if (!loadedKeys.contains(key)) {
				String value = FileStoreUtil.getFileUrlPathByCode(key);
				if (value != null) {
					Set<String> set = new HashSet<>(loadedKeys);
					set.add(key);
					format(out, value, set);
				}
			}
			startIndex = matcher.end();
		}
		endIndex = pattern.length();
		if (startIndex < endIndex) {
			out.append(pattern, startIndex, endIndex);
		}
	}
	
	// 字符获取:获取安全字符并安全剪断两边
	public static String getSafeAndTrim(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString().trim();
		}
	}
	
	// 字符检查:是否字符串为空
	public static Boolean isBlank(String str) {
		int strLen;
		if ((str == null) || ((strLen = str.length()) == 0)) {
			return true;
		}

		// 若为null字符串也表示为空
		if (str.trim().equalsIgnoreCase("null")) {
			return true;
		}

		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	// 获取带星号的手机号码
	public static String getDxhMobileNumber(String number) {
		if (!isBlank(number)) {
			if (number.length() == 11) {
				return number.substring(0, 3) + "****" + number.substring((number.length() - 4), number.length());
			}
		}
		return number;
	}
}
