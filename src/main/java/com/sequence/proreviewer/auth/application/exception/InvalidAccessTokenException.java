package com.sequence.proreviewer.auth.application.exception;

import com.sequence.proreviewer.common.error.BaseCustomException;
import com.sequence.proreviewer.common.error.ErrorCode;

public class InvalidAccessTokenException extends BaseCustomException {

	public InvalidAccessTokenException() {
		super(ErrorCode.INVALID_ACCESS_TOKEN);
	}
}
