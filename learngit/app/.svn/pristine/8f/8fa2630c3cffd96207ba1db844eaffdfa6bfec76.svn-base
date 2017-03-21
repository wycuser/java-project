package com.shanlin.framework.cache;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;

import org.springframework.beans.factory.DisposableBean;

import com.shanlin.p2p.app.constant.MemcachedKeyConfig;

/**
 * 
 * @author zheng xin
 * @createDate 2015年3月31日
 */
public interface SlMemcachedClient extends DisposableBean{

	/**
	 * Get方法, 转换结果类型并屏蔽异常, 仅返回Null.
	 */
	<T> T get(String key);

	/**
	 * GetBulk方法, 转换结果类型并屏蔽异常.
	 */
	<T> Map<String, T> getBulk(Collection<String> keys);

	/**
	 * 异步Set方法, 不考虑执行结果.
	 */
	void set(String key, int expiredTime, Object value);

	/**
	 * 安全的Set方法, 保证在updateTimeout秒内返回执行结果, 否则返回false并取消操作.
	 */
	boolean safeSet(String key, int expiration, Object value);

	/**
	 * 异步 Delete方法, 不考虑执行结果.
	 */
	void delete(String key);

	/**
	 * 安全的Delete方法, 保证在updateTimeout秒内返回执行结果, 否则返回false并取消操作.
	 */
	boolean safeDelete(String key);

	/**
	 * Incr方法.
	 */
	long incr(String key, int by, long defaultValue);

	/**
	 * Decr方法.
	 */
	long decr(String key, int by, long defaultValue);

	/**
	 * 异步Incr方法, 不支持默认值, 若key不存在返回-1.
	 */
	Future<Long> asyncIncr(String key, int by);

	/**
	 * 异步Decr方法, 不支持默认值, 若key不存在返回-1.
	 */
	Future<Long> asyncDecr(String key, int by);
	
	/**
	 * 保存数据到memcached
	 * @param config MemcachedKey 配置
	 * @param suffix key的后缀
	 * @param value 数据
	 */
	void set(MemcachedKeyConfig config, Object suffix, Object value);
	
	/**
	 * 校验数据与存放在memcached的数据是否一致
	 * @param config MemcachedKey 配置
	 * @param suffix key的后缀
	 * @param checkVal 校验的数据
	 * @return
	 */
	boolean check(MemcachedKeyConfig config, String suffix, Object checkVal);

}