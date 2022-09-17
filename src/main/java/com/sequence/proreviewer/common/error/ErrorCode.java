package com.sequence.proreviewer.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	INVALID_GITHUB_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED.value(), "INVALID_GITHUB_ACCESS_TOKEN"),
	INVALID_AUTHORIZATION_CODE(HttpStatus.UNAUTHORIZED.value(), "INVALID_AUTHORIZATION_CODE");


	private final int status;
	private final String message;
}
