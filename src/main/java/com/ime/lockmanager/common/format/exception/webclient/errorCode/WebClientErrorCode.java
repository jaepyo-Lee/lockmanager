package com.ime.lockmanager.common.format.exception.webclient.errorCode;

import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WebClientErrorCode implements ErrorEnumCode {
    SEJONG_INCORRECT_INFORM("WE001", "입력하신 정보를 확인해주세요"),
    WEBCLIENT_AUTH_SERVER_ERROR("WE002","로그인 서버 오류입니다. 관리자에게 문의해주세요.");
    private final String code;
    private final String message;
}
