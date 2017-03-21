package com.shanlin.framework.filter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.shanlin.framework.cache.SlMemcachedClient;
import com.shanlin.framework.config.PropertyFileConfigurer;
import com.shanlin.framework.controller.InterfaceResultCode;
import com.shanlin.framework.mapper.JsonMapper;
import com.shanlin.framework.security.utils.Cryptos;
import com.shanlin.framework.utils.SplitUtil;
import com.shanlin.p2p.app.constant.MemcachedKeyConfig;

/**
 * Servlet Filter implementation class AppCheckFilter app 请求校验Filter
 */
@Component
public class AppCheckFilter implements Filter {

	private final static Logger log = LoggerFactory.getLogger(AppCheckFilter.class);

	/** 不需要解密的uri */
	private static Set<String> unDecryptUri;

	/** 不需要登录的uri */
	private static Set<String> unLoginUri;
	
	/** 不需要serverTime的uri */
	private static Set<String> unServerTimeUri;

	@Resource
	private SlMemcachedClient memcachedClient;
	
	static {
		String unDecryptUriStr = PropertyFileConfigurer.getContextProperty("unDecryptUri");
		String unLoginUriStr = PropertyFileConfigurer.getContextProperty("unLoginUri");
		String unServerTimeUriStr = PropertyFileConfigurer.getContextProperty("unServerTimeUri");
		List<String> unDecryptUriList = new ArrayList<String>();
		List<String> unLoginUriList = new ArrayList<String>();
		List<String> unServerTimeUriList = new ArrayList<String>();
		SplitUtil.split(unDecryptUriStr, unDecryptUriList, ",", 1, 0);
		SplitUtil.split(unLoginUriStr, unLoginUriList, ",", 1, 0);
		SplitUtil.split(unServerTimeUriStr, unServerTimeUriList, ",", 1, 0);
		unDecryptUri = new HashSet<String>(unDecryptUriList);
		unLoginUri = new HashSet<String>(unLoginUriList);
		unServerTimeUri = new HashSet<String>(unServerTimeUriList);
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpBuffResponseWrapper buffResp = new HttpBuffResponseWrapper(resp);
		HttpBuffRequestWrapper buffReq = null;
		// 访问的URI
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		uri = uri.substring(uri.indexOf(contextPath) + contextPath.length() + 1, uri.indexOf(".action"));
		if (uri.equals("server/time")) {
			chain.doFilter(request, response);
			return;
		}
		log.info(uri);
		// json转换器
		JsonMapper jsonMapper = JsonMapper.normalMapper();
		String agenterid = req.getParameter("agenterid");
		// 代理商密码
		String serverTimeStr = req.getParameter("serverTime");
		String agenterPwd = "1AB65E79FC4DA18F";
		String version = req.getParameter("version");
		String messengerid = req.getParameter("messengerid");
		String timestamp = req.getParameter("timestamp");
		String digest = req.getParameter("digest");
		String body = req.getParameter("body");

		String bodyDecry = null;
		Map<String, Object> bodyMap = Collections.emptyMap();
		boolean isLegal = true;
		boolean isUnDecrypt = true;
		String errorCode = InterfaceResultCode.ILLEGAL_REQUEST;
		try {
			// 1、判断时间是否正确
			Long serverTime = null;
			try {serverTime = Long.valueOf(serverTimeStr);} catch (NumberFormatException n) {}
			if (!unServerTimeUri.contains(uri) && (serverTime == null || (System.currentTimeMillis() - serverTime) > 20000L)) {
				log.error("非法请求：serverTime非法，serverTime：{}", serverTimeStr);
				isLegal = false;
			} else {
				// 2、判断url是否需要检验加密
				if (!unDecryptUri.contains(uri)) {
					isUnDecrypt = false;
					// AES解密 body
					if (body != null) {
						bodyDecry = Cryptos.aesDecrypt(body, agenterPwd);
						log.debug("解密后body数据：{}", bodyDecry);
						bodyMap = jsonMapper.fromJson(bodyDecry, HashMap.class);
					}
				}
				buffReq = new HttpBuffRequestWrapper(req, bodyMap);
				// 不需要校验或者校验通过
				if (isUnDecrypt || checkEncryption(version, timestamp, agenterPwd, bodyDecry, digest)) {
					// 3、判断url是否需要校验登录
					if (!unLoginUri.contains(uri)) {
						String userId = buffReq.getParameter("userId");
						String token = buffReq.getParameter("token");
						// 没有用户id
						if (userId == null || token == null || !memcachedClient.check(MemcachedKeyConfig.LOGIN_TOKEN, userId, token)) {
							log.error("非法请求：未登录，userId：{} token：{}", userId, token);
							isLegal = false;
							errorCode = InterfaceResultCode.NOT_LOGIN;
						}
					}
				} else {
					log.error("非法请求：校验不通过，digest：{}", digest);
					isLegal = false;
				}
			}
		} catch (Exception e) {
			log.error("校验请求时发生异常", e);
			isLegal = false;
		}
		if (isLegal) {
			// 4、放行
			chain.doFilter(buffReq, buffResp);
			// 5、添加加密数据回写
			String returnJson = new String(buffResp.toByteArray(), Charset.forName("UTF-8"));
			Object adviceCode = buffReq.getAttribute("adviceCode");
			boolean isStr = false;
			if (adviceCode != null) {
				returnJson = getReturnJson(version, messengerid, timestamp, digest, agenterid, adviceCode.toString(), returnJson, null, true);
				log.debug("异常处理后返回的数据：{}", returnJson);
				resp.setContentLength(returnJson.getBytes(Charset.forName("UTF-8")).length);
				resp.getWriter().write(returnJson);
				return;
			}
			if (!StringUtils.isBlank(returnJson) && !isUnDecrypt) {
				log.debug("加密前：{}", returnJson);
				isStr = true;
				digest = encryption(version, timestamp, agenterPwd, returnJson);
				returnJson = Cryptos.aesEncrypt(returnJson, agenterPwd);
			}
			if(resp.getContentType() == null || resp.getContentType().indexOf("json") > 0){
				Boolean isWrapJson = (Boolean) buffReq.getAttribute("isWrapJson");
				if(isWrapJson == null || isWrapJson)
					returnJson = getReturnJson(version, messengerid, timestamp, digest, agenterid, InterfaceResultCode.SUCCESS, null, returnJson, isStr);
				log.debug("处理后返回的数据：{}", returnJson);
			}
			resp.getWriter().write(returnJson);
		} else {
			resp.setContentType("application/json;charset=UTF-8");
			resp.getWriter().write(getReturnJson(version, messengerid, timestamp, digest, agenterid, errorCode, "非法链接", null, true));
		}
	}

	public static String getReturnJson(String version, String messengerid, String timestamp, String digest, String agenterid, String code, String message,
			String body, boolean isStr) {
		StringBuilder sb = new StringBuilder("{");
		if(version != null)
			sb.append("\"version\":\"").append(version).append("\",");
		if(messengerid != null)
			sb.append("\"messengerid\":\"").append(messengerid).append("\",");
		if(timestamp != null)
			sb.append("\"timestamp\":\"").append(timestamp).append("\",");
		if(digest != null)
			sb.append("\"digest\":\"").append(digest).append("\",");
		if(agenterid != null)
			sb.append("\"agenterid\":\"").append(agenterid).append("\",");
		if(code != null)
			sb.append("\"sc\":").append(code).append(",");
		if(body != null && body.length() > 0)
			sb.append("\"body\":").append(isStr ? "\"" : "").append(body).append(isStr ? "\"," : ",");
		if (message != null)
			sb.append("\"mes\":\"").append(message).append("\",");
		sb.deleteCharAt(sb.length() - 1);
		return sb.append("}").toString();
	}

	/**
	 * 校验请求是否合法
	 * 
	 * @param version
	 * @param timestamp
	 * @param agenterPwd
	 * @param body
	 * @param digest
	 * @return
	 */
	public boolean checkEncryption(String version, String timestamp, String agenterPwd, String body, String digest) {
		switch (version) {
		case "1.0":
		default:
			if (timestamp == null || agenterPwd == null || digest == null)
				return false;
			StringBuilder buff = new StringBuilder(timestamp).append(agenterPwd).append(body == null ? "" : body);
			return DigestUtils.md5Hex(buff.toString()).equals(digest);
		}
	}

	public String encryption(String version, String timestamp, String agenterPwd, String body) {
		switch (version) {
		case "1.0":
		default:
			StringBuilder buff = new StringBuilder(timestamp).append(agenterPwd).append(body);
			return DigestUtils.md5Hex(buff.toString());
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}