package com.sequence.proreviewer.common.error;

import lombok.Getter;

@Getter
public class BaseCustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public BaseCustomException(ErrorCode e) {
        super(e.getMessage());
        errorCode = e;
    }
}
