package com.sbs01.utils;

public class Result {
	private boolean valid;
	
	private String errorMessage;
	
	private Result() {
		this(true, null);
	}
	private Result(boolean valid, String errorMessage) {
		this.valid = valid;
		this.errorMessage = errorMessage;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public boolean isValid() {
		return valid;
	}
	public static Result ok() {
		return new Result();
	}
	public static Result fail(String errorMessage) {
		return new Result(false, errorMessage);
	}
}
