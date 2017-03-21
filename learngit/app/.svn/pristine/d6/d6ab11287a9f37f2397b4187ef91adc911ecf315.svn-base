package com.shanlin.framework.cache;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shanlin.p2p.app.constant.MemcachedKeyConfig;

/**
 * 对SpyMemcached Client的二次封装,提供常用的Get/GetBulk/Set/Delete/Incr/Decr函数的同步与异步操作封装.
 * 
 * 未提供封装的函数可直接调用getClient()取出Spy的原版MemcachedClient来使用.
 * 
 * @author zhengxin
 */
public class SpyMemcachedClient implements SlMemcachedClient {

	private static Logger logger = LoggerFactory.getLogger(SpyMemcachedClient.class);

	private MemcachedClient memcachedClient;

	private long shutdownTimeout = 2500;

	private long updateTimeout = 2500;

	/* (non-Javadoc)
	 * @see com.shanlin.framework.cache.SlMemcachedClient#get(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key) {
		try {
			return (T) memcachedClient.get(key);
		} catch (RuntimeException e) {
			handleException(e, key);
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.shanlin.framework.cache.SlMemcachedClient#getBulk(java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> Map<String, T> getBulk(Collection<String> keys) {
		try {
			return (Map<String, T>) memcachedClient.getBulk(keys);
		} catch (RuntimeException e) {
			handleException(e, StringUtils.join(keys, ","));
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.shanlin.framework.cache.SlMemcachedClient#set(java.lang.String, int, java.lang.Object)
	 */
	@Override
	public void set(String key, int expiredTime, Object value) {
		memcachedClient.set(key, expiredTime, value);
	}

	/* (non-Javadoc)
	 * @see com.shanlin.framework.cache.SlMemcachedClient#safeSet(java.lang.String, int, java.lang.Object)
	 */
	@Override
	public boolean safeSet(String key, int expiration, Object value) {
		Future<Boolean> future = memcachedClient.set(key, expiration, value);
		try {
			return future.get(updateTimeout, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			future.cancel(false);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.shanlin.framework.cache.SlMemcachedClient#delete(java.lang.String)
	 */
	@Override
	public void delete(String key) {
		memcachedClient.delete(key);
	}

	/* (non-Javadoc)
	 * @see com.shanlin.framework.cache.SlMemcachedClient#safeDelete(java.lang.String)
	 */
	@Override
	public boolean safeDelete(String key) {
		Future<Boolean> future = memcachedClient.delete(key);
		try {
			return future.get(updateTimeout, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			future.cancel(false);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.shanlin.framework.cache.SlMemcachedClient#incr(java.lang.String, int, long)
	 */
	@Override
	public long incr(String key, int by, long defaultValue) {
		return memcachedClient.incr(key, by, defaultValue);
	}

	/* (non-Javadoc)
	 * @see com.shanlin.framework.cache.SlMemcachedClient#decr(java.lang.String, int, long)
	 */
	@Override
	public long decr(String key, int by, long defaultValue) {
		return memcachedClient.decr(key, by, defaultValue);
	}

	/* (non-Javadoc)
	 * @see com.shanlin.framework.cache.SlMemcachedClient#asyncIncr(java.lang.String, int)
	 */
	@Override
	public Future<Long> asyncIncr(String key, int by) {
		return memcachedClient.asyncIncr(key, by);
	}

	/* (non-Javadoc)
	 * @see com.shanlin.framework.cache.SlMemcachedClient#asyncDecr(java.lang.String, int)
	 */
	@Override
	public Future<Long> asyncDecr(String key, int by) {
		return memcachedClient.asyncDecr(key, by);
	}

	private void handleException(Exception e, String key) {
		logger.warn("spymemcached client receive an exception with key:" + key, e);
	}

	@Override
	public void destroy() throws Exception {
		if (memcachedClient != null) {
			memcachedClient.shutdown(shutdownTimeout, TimeUnit.MILLISECONDS);
		}
	}

	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	public void setUpdateTimeout(long updateTimeout) {
		this.updateTimeout = updateTimeout;
	}

	public void setShutdownTimeout(long shutdownTimeout) {
		this.shutdownTimeout = shutdownTimeout;
	}
	
	@Override
	public void set(MemcachedKeyConfig config, Object suffix, Object value) {
		set(config.getPrefix() + suffix.toString(), config.getExpiredTime(), value);
	}

	@Override
	public boolean check(MemcachedKeyConfig config, String suffix, Object checkVal) {
		if(checkVal == null)
			return false;
		return checkVal.equals(memcachedClient.get(config.getPrefix() + suffix));
	}
}