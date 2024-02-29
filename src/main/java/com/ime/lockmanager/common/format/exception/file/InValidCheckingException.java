package com.ime.lockmanager.common.format.exception.file;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import lombok.Getter;

import static com.ime.lockmanager.common.format.exception.file.errorCode.FileErrorCode.INVALID_CHECKING_ERROR;

@Getter
public class InValidCheckingException extends ApplicationRunException {
    private final static ErrorEnumCode CODE = INVALID_CHECKING_ERROR;
    public InValidCheckingException(){
        this(CODE);
    }
    private InValidCheckingException(ErrorEnumCode CODE){
        super(CODE);
    }
}
