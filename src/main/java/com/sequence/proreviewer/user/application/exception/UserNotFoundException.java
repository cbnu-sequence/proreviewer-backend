package com.sequence.proreviewer.user.application.exception;

import com.sequence.proreviewer.common.error.BaseCustomException;
import com.sequence.proreviewer.common.error.ErrorCode;

public class UserNotFoundException extends BaseCustomException {

	public UserNotFoundException() {
		super(ErrorCode.USER_NOT_FOUND);
	}
}
