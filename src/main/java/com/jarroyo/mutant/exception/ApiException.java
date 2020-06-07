package com.jarroyo.mutant.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public class ApiException {

	private final String message;
	private final Throwable throwable;
	private final HttpStatus httpStatus;
	private final ZonedDateTime timesTamp;
	public ApiException(String message, 
						Throwable throwable, 
						HttpStatus httpStatus, 
						ZonedDateTime timesTamp) {
		this.message = message;
		this.throwable = throwable;
		this.httpStatus = httpStatus;
		this.timesTamp = timesTamp;
	}
	public String getMessage() {
		return message;
	}
	public Throwable getThrowable() {
		return throwable;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public ZonedDateTime getTimesTamp() {
		return timesTamp;
	}
}
