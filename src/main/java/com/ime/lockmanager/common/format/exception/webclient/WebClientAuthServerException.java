package com.ime.lockmanager.common.format.exception.webclient;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;

import static com.ime.lockmanager.common.format.exception.webclient.errorCode.WebClientErrorCode.WEBCLIENT_AUTH_SERVER_ERROR;

public class WebClientAuthServerException extends ApplicationRunException {
    private static final ErrorEnumCode CODE = WEBCLIENT_AUTH_SERVER_ERROR;

    private WebClientAuthServerException(ErrorEnumCode CODE) {
        super(CODE);
    }

    public WebClientAuthServerException() {
        this(CODE);
    }
}
