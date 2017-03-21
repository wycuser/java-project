package com.shanlin.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shanlin.p2p.app.constant.Constants;

public final class FileStoreUtil {
	
	private static Logger log = LoggerFactory.getLogger(FileStoreUtil.class);

	/**
	 * 
	 * @return
	 */
	public static String getFileUrlPathByCode(String fileCode) {
		if(fileCode == null)
			return null;
		try {
			int end = fileCode.lastIndexOf('.');
			String suffix = null;
			if (end != -1) {
				if (end + 1 < fileCode.length()) {
					suffix = fileCode.substring(end + 1);
				}
				fileCode = fileCode.substring(0, end);
			}
			String[] items = fileCode.split("-");
			if (items.length != 5) {
				return null;
			}
			final int year = Integer.parseInt(items[0], 16);
			final int month = Integer.parseInt(items[1], 16);
			final int day = Integer.parseInt(items[2], 16);
			final int type = Integer.parseInt(items[3], 16);
			final int id = Integer.parseInt(items[4], 16);
			
			StringBuilder url = new StringBuilder();
			url.append(Constants.SYS_BANNER_URL_SUFFIX)
				.append('/').append(type).append('/').append(year).append('/')
				.append(month).append('/').append(day).append('/').append(id);
			if (suffix != null) {
			  url.append('.').append(suffix);
			}
			return url.toString();
		} catch (Exception e) {
			log.error("获取路径异常", e.getMessage());
		}
		return null;
	}
}
