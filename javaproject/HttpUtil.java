package com.synchroni;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


public final class HttpUtil {
private HttpUriRequest request;
	
	private HttpUtil(){}
	
	public static HttpUtil create(){
		return new HttpUtil();
	}
	
	/**
	 * Get 请求 无参数
	 * @param url
	 * @return
	 */
	public HttpUtil get(String url){
		request = new HttpGet(url);
		return this;
	}
	
	/**
	 * Get 请求 带参数
	 * @param url
	 * @param params
	 * @return
	 */
	public HttpUtil get(String url, Map<String, String> params){
		StringBuilder buff = new StringBuilder(url);
		if(params != null && params.size() > 0){
			try {
				buff.append("?");
				for (Entry<String, String> entry : params.entrySet()) {
						buff.append(entry.getKey()).append("=")
							.append(URLEncoder.encode(entry.getValue(), "UTF-8")).append("&");
				}
			} catch (UnsupportedEncodingException e) {
				throw new IllegalArgumentException("params encoder error:" + e.getMessage());
			}
			buff.deleteCharAt(buff.length() - 1);
		}
		return get(buff.toString());
	}
	
	/**
	 * Post 请求 无参数
	 * @param url
	 * @return
	 */
	public HttpUtil post(String url){
		request = new HttpPost(url);
		return this;
	}
	
	/**
	 * Post 请求 带参数
	 * @param url
	 * @param params
	 * @return
	 */
	public HttpUtil post(String url, Map<String, String> params){
		HttpPost post = new HttpPost(url);
		if(params != null && params.size() > 0){
			List<NameValuePair> qparams = new ArrayList<>();
			for (Entry<String, String> entry : params.entrySet()) {
				qparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			try {
				post.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw new IllegalArgumentException("params encoder error:" + e.getMessage());
			}
		}
		request = post;
		return this;
	}
	
	/**
	 * 添加请求头
	 * @param key
	 * @param value
	 * @return
	 */
	public HttpUtil addHeader(String key, String value){
		if(request == null)
			throw new NullPointerException("Please create Request!");
		request.addHeader(key, value);
		return this;
	}
	
	/**
	 * 发送Http请求返回数据
	 * @return
	 */
	public String execute(){
		if(request == null)
			throw new NullPointerException("Please create Request!");
		CloseableHttpClient client = HttpClients.createDefault();
		try (CloseableHttpResponse response = client.execute(request);){
			HttpEntity entity = response.getEntity();
	        if (entity != null) {  
	        	return EntityUtils.toString(entity, "UTF-8");
	        }  
		} catch (IOException e) {
			throw new RuntimeException("connection fails, url:" + request.getURI().toString() + " error:" + e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> execute2Map(){
		return execute2Obj(HashMap.class);
	}
	
	public <T>T execute2Obj(Class<T> clazz){
		ObjectMapper mapper = new ObjectMapper();
		// 设置输出时包含属性的风格
		mapper.setSerializationInclusion(Include.ALWAYS);
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		try {
			return mapper.readValue(execute(), clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
   public static void main(String[] args) {
		try {
			String url = "https://easybuy.jd.com//address/getProvinces.action";
			String retudata = HttpUtil.create().post(url).execute();
			System.out.println(retudata);
		
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
