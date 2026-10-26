package com.temple.tdb_vazhipadu_backend.exception;

public class DailyLimitExceededException extends RuntimeException {
	
	 public DailyLimitExceededException(String message) {
	        super(message);
	    }

}
