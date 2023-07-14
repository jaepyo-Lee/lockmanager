package com.ime.lockmanager.common.exception.locker;

import com.ime.lockmanager.common.exception.ApplicationRunException;
import com.ime.lockmanager.common.exception.ErrorEnumCode;

import static com.ime.lockmanager.common.exception.locker.errorCode.LockerErrorCode.ALREADY_RESERVERD_LOCKER;

public class AlreadyReservedLockerException extends ApplicationRunException {
    private static final ErrorEnumCode CODE = ALREADY_RESERVERD_LOCKER;
    private AlreadyReservedLockerException(ErrorEnumCode errorEnumCode){
        super(errorEnumCode);
    }

    public AlreadyReservedLockerException(){
        this(CODE);
    }
}
