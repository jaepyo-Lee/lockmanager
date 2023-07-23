package com.ime.lockmanager.common.format.exception.auth.errorCode;

import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorEnumCode {
    INVALID_LOGIN_PARAM("A001","잘못된 로그인 정보입니다."),
    INVALID_REFRESH_TOKEN("A002","올바르지 않는 refresh token입니다."),
    BLACKLIST_REFRESH_TOKEN("A003","로그아웃된 refresh token입니다.");

    private final String code;
    private final String message;
}
