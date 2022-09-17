package com.sequence.proreviewer.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
	INVALID_AUTHORIZATION_CODE(401, "INVALID_AUTHORIZATION_CODE");

	private final int status;
	private final String message;

	ErrorCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
