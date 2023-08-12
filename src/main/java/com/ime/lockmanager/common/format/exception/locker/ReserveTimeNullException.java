package com.ime.lockmanager.common.format.exception.locker;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import com.ime.lockmanager.common.format.exception.locker.errorCode.LockerErrorCode;

public class ReserveTimeNullException extends ApplicationRunException {
    private static ErrorEnumCode CODE = LockerErrorCode.RESESRVE_TIME_NULL;
    private ReserveTimeNullException(ErrorEnumCode errorEnumCode) {
        super(errorEnumCode);
    }
    public ReserveTimeNullException(){
        this(CODE);
    }
}
