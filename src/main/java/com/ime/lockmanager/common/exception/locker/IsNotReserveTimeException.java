package com.ime.lockmanager.common.exception.locker;

import com.ime.lockmanager.common.exception.ApplicationRunException;
import com.ime.lockmanager.common.exception.ErrorEnumCode;

import static com.ime.lockmanager.common.exception.locker.LockerErrorCode.IS_NOT_RESERVED_TIME;

public class IsNotReserveTimeException extends ApplicationRunException {
    private static ErrorEnumCode CODE = IS_NOT_RESERVED_TIME;

    public IsNotReserveTimeException() {
        this(CODE);
    }

    private IsNotReserveTimeException(ErrorEnumCode errorEnumCode) {
        super(CODE);
    }
}
