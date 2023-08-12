package com.ime.lockmanager.common.format.exception.auth;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import com.ime.lockmanager.common.format.exception.auth.errorCode.AuthErrorCode;

public class InvalidLoginParamException extends ApplicationRunException {
    private static final ErrorEnumCode CODE = AuthErrorCode.INVALID_LOGIN_PARAM;

    public InvalidLoginParamException(){
        this(CODE);
    }
    private InvalidLoginParamException(ErrorEnumCode errorEnumCode) {
        super(errorEnumCode);
    }
}
