package com.ime.lockmanager.common.format.exception.auth.jwt;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import com.ime.lockmanager.common.format.exception.auth.errorCode.AuthErrorCode;

import static com.ime.lockmanager.common.format.exception.auth.errorCode.JwtErrorCode.INVALID_REFRESH_TOKEN;

public class InvalidRefreshTokenException extends ApplicationRunException {
    private static final ErrorEnumCode CODE = INVALID_REFRESH_TOKEN;

    public InvalidRefreshTokenException(){
        this(CODE);
    }
    private InvalidRefreshTokenException(ErrorEnumCode errorEnumCode) {
        super(errorEnumCode);
    }
}
