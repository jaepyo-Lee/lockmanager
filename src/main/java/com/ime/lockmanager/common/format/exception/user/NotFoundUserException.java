package com.ime.lockmanager.common.format.exception.user;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import com.ime.lockmanager.common.format.exception.user.errorCode.UserErrorCode;

public class NotFoundUserException extends ApplicationRunException {
    private static final ErrorEnumCode CODE = UserErrorCode.NOT_FOUND_USER;

    private NotFoundUserException(ErrorEnumCode code){
        super(code);
    }

    public NotFoundUserException(){
        this(CODE);
    }
}
