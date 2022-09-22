package com.sequence.proreviewer.posts.common.exception;

import com.sequence.proreviewer.common.error.BaseCustomException;
import com.sequence.proreviewer.common.error.ErrorCode;

public class PostNotFoundException extends BaseCustomException {

    public PostNotFoundException(){
        super(ErrorCode.POST_NOT_FOUND);
    }
}
