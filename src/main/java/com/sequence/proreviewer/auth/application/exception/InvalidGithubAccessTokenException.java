package com.sequence.proreviewer.auth.application.exception;

import com.sequence.proreviewer.common.error.BaseCustomException;
import com.sequence.proreviewer.common.error.ErrorCode;

public class InvalidGithubAccessTokenException extends BaseCustomException {

	public InvalidGithubAccessTokenException() {
		super(ErrorCode.INVALID_GITHUB_ACCESS_TOKEN);
	}
}
