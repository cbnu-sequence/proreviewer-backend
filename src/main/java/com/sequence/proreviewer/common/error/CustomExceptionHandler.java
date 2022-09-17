package com.sequence.proreviewer.common.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ErrorResponse> ExceptionHandler(BaseException e) {
		ErrorCode errorCode = e.getErrorCode();

		log.error(errorCode.getMessage());
		ErrorResponse res = new ErrorResponse(errorCode);
		return new ResponseEntity<>(res, HttpStatus.valueOf(errorCode.getStatus()));
	}
}
