package com.ime.lockmanager.common.exception.locker;

import com.ime.lockmanager.common.exception.ApplicationRunException;
import com.ime.lockmanager.common.exception.ErrorEnumCode;

import static com.ime.lockmanager.common.exception.locker.errorCode.LockerErrorCode.RESESRVE_TIME_NULL;

public class ReserveTimeNullException extends ApplicationRunException {
    private static ErrorEnumCode CODE = RESESRVE_TIME_NULL;
    private ReserveTimeNullException(ErrorEnumCode errorEnumCode) {
        super(errorEnumCode);
    }
    public ReserveTimeNullException(){
        this(CODE);
    }
}
