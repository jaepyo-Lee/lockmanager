package com.ime.lockmanager.common.exception.auth;

import com.ime.lockmanager.common.exception.ApplicationRunException;
import com.ime.lockmanager.common.exception.ErrorEnumCode;

import static com.ime.lockmanager.common.exception.auth.errorCode.AuthErrorCode.INVALID_LOGIN_PARAM;

public class InvalidLoginParamException extends ApplicationRunException {
    private static final ErrorEnumCode CODE = INVALID_LOGIN_PARAM;

    public InvalidLoginParamException(){
        this(CODE);
    }
    private InvalidLoginParamException(ErrorEnumCode errorEnumCode) {
        super(errorEnumCode);
    }
}
