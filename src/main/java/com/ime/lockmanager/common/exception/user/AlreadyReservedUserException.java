package com.ime.lockmanager.common.exception.user;

import com.ime.lockmanager.common.exception.ApplicationException;
import com.ime.lockmanager.common.exception.ErrorEnumCode;

import static com.ime.lockmanager.common.exception.user.UserErrorCode.ALREADY_RESERVED_USER;

public class AlreadyReservedUserException extends ApplicationException {
    private static final ErrorEnumCode CODE = ALREADY_RESERVED_USER;
    private AlreadyReservedUserException(ErrorEnumCode errorEnumCode){
        super(errorEnumCode);
    }
    public AlreadyReservedUserException(){
        this(CODE);
    }
}
