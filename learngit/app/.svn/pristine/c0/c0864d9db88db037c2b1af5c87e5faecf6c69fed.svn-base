package com.shanlin.framework.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertyFileConfigurer extends PropertyPlaceholderConfigurer {

	/*
	 * 存入所有properties文件的键值对
	 */
	private static Map<String, String> ctxPropertiesMap;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		ctxPropertiesMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}

	/**
	 * 根据key取value
	 * 
	 * @param name
	 * @return
	 */
	public static String getContextProperty(String name) {
		return ctxPropertiesMap.get(name);
	}
	
	/**
	 * 根据key取value
	 * 
	 * @param name
	 * @param def
	 * @return
	 */
	public static String getContextProperty(String name, String def) {
		return ctxPropertiesMap.containsKey(name)? ctxPropertiesMap.get(name) : def;
	}
	
}
