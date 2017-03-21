package com.shanlin.p2p.app.constant;

/**
 * 统一定义Memcached中存储的各种对象的Key前缀和超时时间.
 */
public enum MemcachedKeyConfig {
	
	/** 验证码 失效时间3分钟*/
	VERIFYCODE("p2p.app.verifycode:", 60 * 3),
	
	/** 登录令牌 失效时间1个月*/
	LOGIN_TOKEN("p2p.app.login.token:", 30 * 24 * 60 * 60),
	
	/** 登录名称 失效时间1个月*/
	LOGIN_NAME("p2p.app.login.name:", 30 * 24 * 60 * 60),
	
	/** 修改密码 30分钟有效*/
	RESET_PASSWORD("p2p.app.reset.password:", 30 * 60),
	
	/** 修改提现密码 30分钟有效*/
	RESET_MONEY_PASSWORD("p2p.app.reset.moneypass:", 30 * 60);
	
	private String prefix;
	private int expiredTime;

	MemcachedKeyConfig(String prefix, int expiredTime) {
		this.prefix = prefix;
		this.expiredTime = expiredTime;
	}

	public String getPrefix() {
		return prefix;
	}

	public int getExpiredTime() {
		return expiredTime;
	}

}
