package com.ime.lockmanager.common.format.exception.user;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import com.ime.lockmanager.common.format.exception.user.errorCode.UserErrorCode;

public class AlreadyReservedUserException extends ApplicationRunException {
    private static final ErrorEnumCode CODE = UserErrorCode.ALREADY_RESERVED_USER;
    private AlreadyReservedUserException(ErrorEnumCode errorEnumCode){
        super(errorEnumCode);
    }
    public AlreadyReservedUserException(){
        this(CODE);
    }
}
