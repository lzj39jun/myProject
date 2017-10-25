package com.thinkgem.jeesite.common.utils;

import java.io.Serializable;


public class JsonVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4219343823707295863L;

	/**
	 * code 返回状态码
	 */
	private  int code=200;

	/**
	 * 异常消息
	 */
	private  String message="";

	/**
	 * 封装的对象
	 */
	private  Object result;

	public  JsonVO() {

	}

	public JsonVO(int code, String message, Object result){
		this.code = code;
		this.message = message;
		this.result = result;
	}

	public  JsonVO(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	/**
	 * 直接构造包含值的对象
	 * 
	 * @param r
	 */
	public JsonVO(Object r) {
		//this();
		this.result = r;
	}

	/**
	 * 
	 * 以下get,set 方法
	 */

	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		if(result==null){
			return "";
		}
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
}

