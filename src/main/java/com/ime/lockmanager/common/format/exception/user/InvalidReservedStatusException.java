package com.ime.lockmanager.common.format.exception.user;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import com.ime.lockmanager.common.format.exception.user.errorCode.UserErrorCode;

import static com.ime.lockmanager.common.format.exception.user.errorCode.UserErrorCode.INVALID_RESERVED_STATUS;

public class InvalidReservedStatusException extends ApplicationRunException {
    private static final ErrorEnumCode CODE = INVALID_RESERVED_STATUS;

    private InvalidReservedStatusException(ErrorEnumCode code){
        super(code);
    }

    public InvalidReservedStatusException(){
        this(CODE);
    }
}
