package com.ime.lockmanager.common.format.exception.locker;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;

import static com.ime.lockmanager.common.format.exception.locker.errorCode.LockerErrorCode.INVALID_LOCKER_DETAIL;

public class InvalidLockerDetailException extends ApplicationRunException {
    private final static ErrorEnumCode CODE = INVALID_LOCKER_DETAIL;
    public InvalidLockerDetailException(ErrorEnumCode CODE){
        super(CODE);
    }

    public InvalidLockerDetailException() {
        this(CODE);
    }
}
