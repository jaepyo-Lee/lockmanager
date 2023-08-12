package com.ime.lockmanager.common.format.exception.locker;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;

import static com.ime.lockmanager.common.format.exception.locker.errorCode.LockerErrorCode.INVALID_CANCEL_LOCKER;

public class InvalidCancelLockerException extends ApplicationRunException {
    private static final ErrorEnumCode CODE = INVALID_CANCEL_LOCKER;
    public InvalidCancelLockerException(){
        this(CODE);
    }
    private InvalidCancelLockerException(ErrorEnumCode code){
        super(code);
    }

}
