package com.ime.lockmanager.common.format.exception.file;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import lombok.RequiredArgsConstructor;

import static com.ime.lockmanager.common.format.exception.file.errorCode.FileErrorCode.NOT_VALID_EXCEL_FORMAT;

public class NotValidExcelFormatException extends ApplicationRunException {
    private final static ErrorEnumCode CODE=NOT_VALID_EXCEL_FORMAT;
    private NotValidExcelFormatException(ErrorEnumCode CODE){
        super(CODE);
    }
    public NotValidExcelFormatException(){
        this(CODE);
    }
}
