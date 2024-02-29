package com.ime.lockmanager.common.format.exception.major.majordetail;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;

import static com.ime.lockmanager.common.format.exception.major.majordetail.errorCode.MajorDetailErrorCode.DUPLICATED_MAJOR_DETAIL_ERROR;

public class DuplicatedMajorDetailException extends ApplicationRunException {
    private final static ErrorEnumCode CODE = DUPLICATED_MAJOR_DETAIL_ERROR;

    private DuplicatedMajorDetailException(ErrorEnumCode errorEnumCode) {
        super(errorEnumCode);
    }

    public DuplicatedMajorDetailException(){
        this(CODE);
    }
}
