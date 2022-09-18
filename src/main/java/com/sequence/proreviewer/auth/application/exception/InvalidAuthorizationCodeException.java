package com.sequence.proreviewer.auth.application.exception;

import com.sequence.proreviewer.common.error.BaseCustomException;
import com.sequence.proreviewer.common.error.ErrorCode;

public class InvalidAuthorizationCodeException extends BaseCustomException {

	public InvalidAuthorizationCodeException() {
		super(ErrorCode.INVALID_AUTHORIZATION_CODE);
	}
}
