package com.ime.lockmanager.common.format.exception.locker;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import com.ime.lockmanager.common.format.exception.locker.errorCode.LockerErrorCode;

public class IsNotReserveTimeException extends ApplicationRunException {
    private static ErrorEnumCode CODE = LockerErrorCode.IS_NOT_RESERVED_TIME;

    public IsNotReserveTimeException() {
        this(CODE);
    }

    private IsNotReserveTimeException(ErrorEnumCode errorEnumCode) {
        super(CODE);
    }
}
