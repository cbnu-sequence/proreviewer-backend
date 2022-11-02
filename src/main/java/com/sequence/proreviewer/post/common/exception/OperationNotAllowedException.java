package com.sequence.proreviewer.post.common.exception;

import com.sequence.proreviewer.common.error.BaseCustomException;
import com.sequence.proreviewer.common.error.ErrorCode;

public class OperationNotAllowedException extends BaseCustomException {

    public OperationNotAllowedException(){
        super(ErrorCode.OPERATION_NOT_ALLOWED);
    }
}
