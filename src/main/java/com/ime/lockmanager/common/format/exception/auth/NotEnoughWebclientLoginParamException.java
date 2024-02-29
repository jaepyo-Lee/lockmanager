package com.ime.lockmanager.common.format.exception.auth;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;

import static com.ime.lockmanager.common.format.exception.auth.errorCode.AuthErrorCode.NOT_ENOUGH_WEBCLIENT_LOGIN_PARAM;

public class NotEnoughWebclientLoginParamException extends ApplicationRunException {
    private static final ErrorEnumCode CODE = NOT_ENOUGH_WEBCLIENT_LOGIN_PARAM;


    private NotEnoughWebclientLoginParamException(ErrorEnumCode CODE) {
        super(CODE);
    }

    public NotEnoughWebclientLoginParamException() {
        this(CODE);
    }
}
