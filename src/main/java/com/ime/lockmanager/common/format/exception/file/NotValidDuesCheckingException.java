package com.ime.lockmanager.common.format.exception.file;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import lombok.Getter;

import static com.ime.lockmanager.common.format.exception.file.errorCode.FileErrorCode.NOT_VALID_DUES_CHECKING;

@Getter
public class NotValidDuesCheckingException extends ApplicationRunException {
    private final static ErrorEnumCode CODE = NOT_VALID_DUES_CHECKING;
    public NotValidDuesCheckingException(){
        this(CODE);
    }
    private NotValidDuesCheckingException(ErrorEnumCode CODE){
        super(CODE);
    }
}
