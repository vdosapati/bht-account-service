package com.bht.account.ms.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorcode;
	private String errorMessage;
	private String errorName;
	
	
	public AccountException(String errorcode, String errorMessage, String errorName) {
		this.errorcode = errorcode;
		this.errorMessage = errorMessage;
		this.errorName = errorName;
	}
	
	public AccountException(String errorcode, String errorMessage) {
		this.errorcode = errorcode;
		this.errorMessage = errorMessage;
	}
	
	public AccountException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	
}
