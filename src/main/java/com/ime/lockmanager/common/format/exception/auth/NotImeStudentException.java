package com.ime.lockmanager.common.format.exception.auth;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;

import static com.ime.lockmanager.common.format.exception.auth.errorCode.AuthErrorCode.NOT_IME_STUDENT;

public class NotImeStudentException extends ApplicationRunException {
    private static final ErrorEnumCode CODE = NOT_IME_STUDENT;
    private NotImeStudentException(ErrorEnumCode code){
        super(code);
    }
    public NotImeStudentException(){
        this(CODE);
    }
}
