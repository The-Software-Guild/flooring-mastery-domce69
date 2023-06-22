package com.wileyedge.exception;

public class IncorrectDateInputException extends Exception {
	
	String msg;
	
	public IncorrectDateInputException(String msg) {
		super(msg);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
