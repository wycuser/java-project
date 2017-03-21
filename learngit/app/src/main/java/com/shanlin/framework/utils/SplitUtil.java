package com.shanlin.framework.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *Description : 切割字符串的工具类，用于替换jdk的split方法
 *createDate ：2012-3-2
 *@author 郑鑫
 *@version v1.0
 */
public class SplitUtil {

	/**
	 * 获取字段个数
	 * @param s 原字符串
	 * @param str 分隔符
	 * @param limit 末尾是否以分隔符结尾
	 * @return 字段个数
	 */
	private static int count(String s, String str, int limit) {
		int k = 0;
		
		for (int index = 0;; index += str.length()) {
			index = s.indexOf(str, index);
			if (index == -1) {
				break;
			}
			k++;
		}
		return limit < 1 ? ++k : k;
	}

	/**
	 * 按分隔符分割字符串，取代jdk的String.split方法
	 * @param str 待分割字符串
	 * @param split 分隔符
	 * @param length 分隔符长度
	 * @return 字符串数组
	 */
	public static String[] split(String str, String split, int length) {
		if(!str.endsWith(split)){
			str += split;
		}
		return split(str, split, length, 1);
	}

	/**
	 * 按分隔符分割字符串，取代jdk的String.split方法
	 * @param str 待分割字符串
	 * @param split 分隔符
	 * @param length 分隔符长度
	 * @param limit 是否以分隔符结尾 (1是,0否)
	 * @return 字符串数组
	 */
	public static String[] split(String str, String split, int length, int limit) {
		String[] array = new String[count(str, split, limit)];
		
		if (array.length == 0) {
			return null;
		}
		int index = 0;
		int offset = 0;
		int i = 0;
		
		while ((index = str.indexOf(split, offset)) != -1) {
			array[i] = str.substring(offset, index);
			offset = index + length;
			i++;
		}
		if (limit < 1) {
			array[array.length - 1] = str.substring(str.lastIndexOf(split) + length);
		}
		return array;
	}

	/**
	 * 按分隔符分割字符串，取代jdk的String.split方法
	 * 
	 * @param str 待分割字符串
	 * @param list 存储分割后的字符串集合
	 * @param split 分隔符
	 * @param length 分隔符长度
	 * @param limit 是否以分隔符结尾 (1是,0否)
	 */
	public static void split(String str, List<String> list, 
			String split, int length, int limit) {
		if (list == null) {
			throw new NullPointerException();
		}
		int index = 0;
		int offset = 0;
		
		while ((index = str.indexOf(split, offset)) != -1) {
			list.add(str.substring(offset, index));
			offset = index + length;
		}
		if (limit < 1) {
			list.add(str.substring(str.lastIndexOf(split) + length));
		}
	}
	
	public static void split(String str, List<String> list, 
			String split) {
		split(str, list, split, split.length(), 0);
	}
	
	/**
	 * 按分隔符分割字符串，取代jdk的String.split方法
	 * 
	 * @param str 待分割字符串
	 * @param list 存储分割后的字符串集合
	 * @param split 分隔符
	 * @param length 分隔符长度
	 */
	public static void split(String str, List<String> list, 
			String split, int length) {
		if(!str.endsWith(split)){
			str += split;
		}
		split(str, list, split, length, 1);
	}
	
	/**
	 * 测试方法
	 * @param args 入参
	 */
	public static void main(String[] args) {
		String ods = "120192168169214133721968800429428200196117764#|" 
			+ "10.234.139.12#|10.235.148.9#|10.204.51.97#|13#|10.235.148.9" 
			+ "#|1#|3009#|2012-05-17 09:54:48.053159#|2012-05-17 09:54:48.322079" 
			+ "#|268920#|15323169769#|/orderDeal.jspx?dealType=2&productId=" 
			+ "135000000000000021401&idType=03^M<96>#|200#|1#|10.235.148.9#|" 
			+ "460036230129497#|BREW-Applet/0x010DB517 (BREW/3.1.5.186; " 
			+ "DeviceId:  40157; Lang: zhcn)#|3#|14#|2#|application/xml#|" 
			+ "#|WAP2脨颅脪茅CDR路脰脦枚_214#|20#|20120517095000#|a";
		long start = System.currentTimeMillis();
		int count = 100000;
		
		for (int i = 0; i < count; i++) {
			ods.split("#\\|");
		}
		System.out.println("split耗时" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			split(ods, "#|", "#|".length(), 0);
		}
		System.out.println("Array优化耗时" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		List<String> list = new ArrayList<String>();
		
		for (int i = 0; i < count; i++) {
			split(ods, list, "#|", "#|".length(), 0);
			list.clear();
		}
		System.out.println("ArrayList优化耗时" + (System.currentTimeMillis() - start));
	}

}