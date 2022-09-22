package com.sequence.proreviewer.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED.value(), "INVALID_ACCESS_TOKEN"),
	INVALID_AUTHORIZATION_CODE(HttpStatus.UNAUTHORIZED.value(), "INVALID_AUTHORIZATION_CODE"),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "USER_NOT_FOUND");


	private final int status;
	private final String message;
}
