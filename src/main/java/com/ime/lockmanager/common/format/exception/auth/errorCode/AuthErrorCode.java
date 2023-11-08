package com.ime.lockmanager.common.format.exception.auth.errorCode;

import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorEnumCode {
    INVALID_LOGIN_PARAM("AE001","로그인 정보가 틀렸습니다. 아이디와 비밀번호를 다시 확인해주세요"),

    NOT_IME_STUDENT("AE002","지능기전공학과 학우들만 이용할수 있는 서비스입니다.");

    private final String code;
    private final String message;
}
