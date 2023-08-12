package com.ime.lockmanager.common.format.exception.auth;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import com.ime.lockmanager.common.format.exception.auth.errorCode.AuthErrorCode;

public class InvalidRefreshTokenException extends ApplicationRunException {
    private static final ErrorEnumCode CODE = AuthErrorCode.INVALID_REFRESH_TOKEN;

    public InvalidRefreshTokenException(){
        this(CODE);
    }
    private InvalidRefreshTokenException(ErrorEnumCode errorEnumCode) {
        super(errorEnumCode);
    }
}
