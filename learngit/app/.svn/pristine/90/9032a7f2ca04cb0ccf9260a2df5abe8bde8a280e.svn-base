package com.shanlin.framework.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 定义具有缓存功能response
 * 
 * @author zheng xin
 * @createDate 2015年1月15日
 */
public class HttpBuffResponseWrapper extends HttpServletResponseWrapper {
	
	/** 缓存*/
	private ByteArrayOutputStream baos = null; 
	
	/** 重置response的ServletOutputStream对象*/
	private ByteArrayServletOutputStream byteArrOut = null;
	
	/** 重置response的PrintWriter对象*/
	private PrintWriter pw = null;

	public HttpBuffResponseWrapper(HttpServletResponse response) {
		super(response);
		baos = new ByteArrayOutputStream(8192);
		byteArrOut = new ByteArrayServletOutputStream(baos);
		pw = new PrintWriter(baos);
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return pw;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return byteArrOut;
	}
	
	public byte[] toByteArray(){
		pw.flush();
		return baos.toByteArray();
	}
	
	/** 
	 * Class that extends ServletOuputStream, used as a wrapper from within 
	 * <code>SsiInclude</code> 
	 * 
	 * @author Bip Thelin 
	 * @version $Id: ByteArrayServletOutputStream.java
	 * @see ServletOutputStream and ByteArrayOutputStream 
	 */
	public class ByteArrayServletOutputStream extends ServletOutputStream { 
	    /** 
	     * Our buffer to hold the stream. 
	     */
	    protected ByteArrayOutputStream buf = null; 
	  
	    /** 
	     * Construct a new ServletOutputStream. 
	     */
	    public ByteArrayServletOutputStream(ByteArrayOutputStream baos) { 
	    	buf = baos; 
	    } 
	  
	    /** 
	     * @return the byte array. 
	     */
	    public byte[] toByteArray() { 
	        return buf.toByteArray(); 
	    } 
	  
	    /** 
	     * Write to our buffer. 
	     * 
	     * @param b The parameter to write 
	     */
	    @Override
	    public void write(int b) { 
	        buf.write(b); 
	    } 
	} 
}
