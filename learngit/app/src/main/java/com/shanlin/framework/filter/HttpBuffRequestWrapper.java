package com.shanlin.framework.filter;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shanlin.framework.mapper.JsonMapper;

/**
 * 请求修饰类
 * @author zheng xin
 * @createDate 2015年1月15日
 */
public class HttpBuffRequestWrapper extends HttpServletRequestWrapper{
	
	private static final Logger log = LoggerFactory.getLogger(HttpBuffRequestWrapper.class);
	
	private Map<String, Object> paramMap = null;
	
	private JsonMapper mapper = JsonMapper.normalMapper();
	
	public HttpBuffRequestWrapper(HttpServletRequest request, Map<String, Object> paramMap) {
		super(request);
		this.paramMap = paramMap;
	}

	@Override
	public String getParameter(String name) {
		Object obj = this.paramMap.get(name);
		if(obj == null){
			return super.getParameter(name);
		} else if(obj instanceof String){
			return (String) obj;
		} else if(obj instanceof Number){
			return obj.toString();
		} else {
			return mapper.toJson(obj);
		}
	}
	
	@Override
	public Map<String, String[]> getParameterMap() {
		log.error("not override method");
		return super.getParameterMap();
	}
	
	@Override
	public Enumeration<String> getParameterNames() {
		return new ParamEnumeration(this.paramMap.keySet().iterator());
	}
	
	@Override
	public String[] getParameterValues(String name) {
		return new String[]{this.getParameter(name)};
	}
	
	private class ParamEnumeration implements Enumeration<String>{
		
		private Iterator<String> it = null;
		
		public ParamEnumeration(Iterator<String> it){
			this.it = it;
		}
		
		@Override
		public boolean hasMoreElements() {
			return it.hasNext();
		}

		@Override
		public String nextElement() {
			return it.next();
		}
	};
}
