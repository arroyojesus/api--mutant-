package com.jarroyo.mutant.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ApiExceptionHandler {
	
	@ExceptionHandler(value = {ApiRequestException.class})
	public ResponseEntity<Object> handlerApiRequestException(ApiRequestException e) {
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		ApiException apiException = new ApiException(e.getMessage(), e, HttpStatus.BAD_REQUEST,
				ZonedDateTime.now(ZoneId.of("Z")));
		return new ResponseEntity<>(apiException, badRequest);
	}
}
