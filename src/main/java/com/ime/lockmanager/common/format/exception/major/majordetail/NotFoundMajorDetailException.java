package com.ime.lockmanager.common.format.exception.major.majordetail;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;

import static com.ime.lockmanager.common.format.exception.major.majordetail.errorCode.MajorDetailErrorCode.NOT_FOUND_MAJOR_DETAIL_ERROR;

public class NotFoundMajorDetailException extends ApplicationRunException {
    private final static ErrorEnumCode CODE = NOT_FOUND_MAJOR_DETAIL_ERROR;
    private NotFoundMajorDetailException(ErrorEnumCode CODE) {
        super(CODE);
    }
    public NotFoundMajorDetailException() {
        this(CODE);
    }
}
