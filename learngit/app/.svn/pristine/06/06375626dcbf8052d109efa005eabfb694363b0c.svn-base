package com.shanlin.p2p.app.service;

import java.util.Map;

public interface RemoteInvokeService {
	
	public static final int REGISTER = 16;
	
	public static final int BIND_EMAIL_AUTH = 1;
	
	public static final int BIND_IDCARD_AUTH = 2;
	
	public static final int BIND_MONEY_PASS_AUTH = 3;
	
	public static final int UPLOAD_FAVICON = 4;
	
	public static final int LOGIN = 5;
	
	public static final int INVEST_SUCCESS = 7;
	
	public static final int LOAN_SUCCESS = 8;
	
	public static final int RECOMMENDED = 9;
	
	public static final int EDIT_EMAIL = 10;
	
	public static final int REFERRER = 11;
	
	public static final int QUERY = 12;
	
	public static final int EDIT_MOBILE = 14;
	
	void httpInvoke(String loginName, int api, String args);
	
	Map<String, Object> httpInvokeReturn (String loginName, int api, String args);
	
	void addBbsAccount(final String loginName, final String password, final String mobilePhone);
	
	void updatePass(final String loginName, final String oldPass, final String newPass);
}
