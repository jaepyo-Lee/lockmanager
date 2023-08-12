package com.ime.lockmanager.common.format.exception.user;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;

import static com.ime.lockmanager.common.format.exception.user.errorCode.UserErrorCode.INCORRECT_PASSWORD;

public class IncorrectPasswordException extends ApplicationRunException {
    private static final ErrorEnumCode CODE = INCORRECT_PASSWORD;
    private IncorrectPasswordException(ErrorEnumCode errorEnumCode){
        super(errorEnumCode);
    }
    public IncorrectPasswordException(){
        this(CODE);
    }
}
