package com.jlkj.web.shopnew.exception;


import com.jlkj.web.shopnew.core.StatusCode;

/**
 * 
 * @Description 业务处理异常类
 * @author wzq
 * @date 2018年5月31日 下午1:47:36
 */
public class BussinessException extends RuntimeException {

	private String code;

	private String message;

	public BussinessException(String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public BussinessException(StatusCode statusCode) {
		this.code = statusCode.getCode();
		this.message = statusCode.getMsg();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
