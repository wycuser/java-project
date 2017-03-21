package com.shanlin.framework.controller;

/**
 * 返回给前端ajax请求的结果代码
 * @author zheng xin
 * @createDate 2015年1月15日
 */
public class InterfaceResultCode {

    /**
     * 成功
     */
    public static final String SUCCESS = "200";

    /**
     * 失败
     */
    public static final String FAILED = "0";

    /**
     * 未登录
     */
    public static final String NOT_LOGIN = "1";
    
    /**
     * 包含敏感词汇
     */
    public static final String HAVE_SENSITIVE = "2";
    
    /**
     * 非法请求
     */
    public static final String ILLEGAL_REQUEST = "3";
   
    /**
     * 系统异常
     */
    public static final String SYS_ERROR = "4";
    
}
