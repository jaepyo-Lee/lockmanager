package com.ime.lockmanager.common.exception.locker;

import com.ime.lockmanager.common.exception.ApplicationRunException;
import com.ime.lockmanager.common.exception.ErrorEnumCode;

public class NotFoundLockerException extends ApplicationRunException {
    private static final ErrorEnumCode CODE = LockerErrorCode.NOT_FOUND_LOCKER;
    private NotFoundLockerException(ErrorEnumCode errorEnumCode){
        super(errorEnumCode);
    }
    public NotFoundLockerException(){
        this(CODE);
    }
}