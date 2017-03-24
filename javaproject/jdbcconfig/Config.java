package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Config {
	private static Properties conf = null;//配置文件
	private static Connection conn=null;//数据连接
	
	/**
	 * 读取配置信息
	 * @param filePath 文件路径
	 * @return 配置
	 * @throws IOException
	 */
	public static Properties getProperties(String filePath) throws IOException {
		InputStream inStream = null;
		try {
			if (conf == null) {
				conf = new Properties();
			}
			inStream = Config.class.getClassLoader().getResourceAsStream(
					filePath);
			conf.load(inStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inStream.close();//关闭流
		}
		return conf;
	}
	
	/**
	 * 获得数据库的连接
	 * @return 数据连接
	 */
	public static synchronized Connection getConnection(){
		try {
			Properties conf=Config.getProperties("db.properties");
			Class.forName(conf.getProperty("driver"));
			if(conn==null){
				String url=conf.getProperty("url");
				String user=conf.getProperty("user");
				String password=conf.getProperty("pass");
				conn=DriverManager.getConnection(url, user, password);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
