package com.ime.lockmanager.common.format.exception.user.errorCode;

import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorEnumCode {
    NOT_FOUND_USER("U001", "사용자를 찾지 못했습니다"),
    ALREADY_RESERVED_USER("U002", "이미 사물함을 예약한 사용자 입니다.");
    private final String code;
    private final String message;
}
