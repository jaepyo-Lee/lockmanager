package com.ime.lockmanager.common.exception.locker;

import com.ime.lockmanager.common.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LockerErrorCode implements ErrorEnumCode {
    NOT_FOUND_LOCKER("L001","사물함을 찾지 못했습니다."),
    ALREADY_RESERVERD_LOCKER("L002","이미 예약된 사물함입니다.");
    private final String code;
    private final String message;
}
