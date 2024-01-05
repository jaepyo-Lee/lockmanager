package com.ime.lockmanager.common.format.exception.valid.errorcode;

import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;


// 사용x
@Getter
@AllArgsConstructor
public enum ValidErrorCode implements ErrorEnumCode {
    VALID_NULL_EXCEPTION("VE001", "널입니다.");
    private final String code;
    private final String message;
}
