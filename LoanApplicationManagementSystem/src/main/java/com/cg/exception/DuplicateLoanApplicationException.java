package com.cg.exception;

public class DuplicateLoanApplicationException extends RuntimeException {
	public DuplicateLoanApplicationException(String msg) {
		super(msg);
	}

}
