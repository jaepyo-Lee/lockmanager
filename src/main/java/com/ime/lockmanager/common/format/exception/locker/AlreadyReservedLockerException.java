package com.ime.lockmanager.common.format.exception.locker;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import com.ime.lockmanager.common.format.exception.locker.errorCode.LockerErrorCode;

public class AlreadyReservedLockerException extends ApplicationRunException {
    private static final ErrorEnumCode CODE = LockerErrorCode.ALREADY_RESERVERD_LOCKER;
    private AlreadyReservedLockerException(ErrorEnumCode errorEnumCode){
        super(errorEnumCode);
    }

    public AlreadyReservedLockerException(){
        this(CODE);
    }
}
