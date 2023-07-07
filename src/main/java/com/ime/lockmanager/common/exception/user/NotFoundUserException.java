package com.ime.lockmanager.common.exception.user;

import com.ime.lockmanager.common.exception.ApplicationException;
import com.ime.lockmanager.common.exception.ErrorEnumCode;

import static com.ime.lockmanager.common.exception.user.UserErrorCode.NOT_FOUND_USER;

public class


NotFoundUserException extends ApplicationException {
    private static final ErrorEnumCode CODE = NOT_FOUND_USER;

    private NotFoundUserException(ErrorEnumCode code){
        super(code);
    }

    public NotFoundUserException(){
        this(CODE);
    }
}
