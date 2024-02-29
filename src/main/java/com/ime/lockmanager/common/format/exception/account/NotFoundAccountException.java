package com.ime.lockmanager.common.format.exception.account;

import com.ime.lockmanager.common.format.exception.ApplicationRunException;
import com.ime.lockmanager.common.format.exception.ErrorEnumCode;

import static com.ime.lockmanager.common.format.exception.account.errorcode.AccountErrorCode.NOT_FOUND_ACCOUNT;

public class NotFoundAccountException extends ApplicationRunException {
    private static ErrorEnumCode CODE = NOT_FOUND_ACCOUNT;

    public NotFoundAccountException(ErrorEnumCode errorEnumCode) {
        super(errorEnumCode);
    }

    public NotFoundAccountException() {
        this(CODE);
    }
}
