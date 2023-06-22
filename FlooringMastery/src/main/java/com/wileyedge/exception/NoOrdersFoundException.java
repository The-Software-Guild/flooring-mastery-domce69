package com.wileyedge.exception;

public class NoOrdersFoundException extends Exception {
	
	String msg;
	
	public NoOrdersFoundException(String msg) {
		super(msg);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
