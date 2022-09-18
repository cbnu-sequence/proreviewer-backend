package com.sequence.proreviewer.common.error;

import lombok.Getter;

@Getter
public class ErrorResponse {

	private int status;
	private String message;

	public ErrorResponse(ErrorCode errorCode) {
		this.status = errorCode.getStatus();
		this.message = errorCode.getMessage();
	}
}
