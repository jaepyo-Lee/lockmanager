package com.ime.lockmanager.common.exception.locker;

import com.ime.lockmanager.common.exception.ApplicationException;
import com.ime.lockmanager.common.exception.ErrorEnumCode;

public class NotFoundLockerException extends ApplicationException {
    private static final ErrorEnumCode CODE = LockerErrorCode.NOT_FOUND_LOCKER;
    private NotFoundLockerException(ErrorEnumCode errorEnumCode){
        super(errorEnumCode);
    }
    public NotFoundLockerException(){
        this(CODE);
    }
}