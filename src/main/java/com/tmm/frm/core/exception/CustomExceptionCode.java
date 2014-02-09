package com.tmm.frm.core.exception;

public enum CustomExceptionCode {

	USER001_INVALIDUSER("USER001", "Logged-in account not correctly created - All Accounts must be linked to a Profile"), APP001_NERDABILITYUSER("APP001",
			"Nerdability user not setup correctly - please setup application context user"), 
	USER002_DUPLICATEUSER("USER002", "Attempting to create a duplicate user - username already taken"),
	USER003_DUPLICATEEMAIL("USER003", "Attempting to create a user with an email that is already registered"),
	USER004_INVALIDUSERNAME("USER004","Attempting to create a user with an invalid username");

	private String code;
	private String description;

	private CustomExceptionCode(String c, String desc) {
		setCode(c);
		setDescription(desc);
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}