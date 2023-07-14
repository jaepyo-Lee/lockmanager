package com.ime.lockmanager.common.exception.auth;

import com.ime.lockmanager.common.exception.ApplicationRunException;
import com.ime.lockmanager.common.exception.ErrorEnumCode;

import static com.ime.lockmanager.common.exception.auth.errorCode.AuthErrorCode.INVALID_REFRESH_TOKEN;

public class InvalidRefreshTokenException extends ApplicationRunException {
    private static final ErrorEnumCode CODE = INVALID_REFRESH_TOKEN;

    public InvalidRefreshTokenException(){
        this(CODE);
    }
    private InvalidRefreshTokenException(ErrorEnumCode errorEnumCode) {
        super(errorEnumCode);
    }
}
