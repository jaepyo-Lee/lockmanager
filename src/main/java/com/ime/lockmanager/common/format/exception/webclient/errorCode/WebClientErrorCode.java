package com.ime.lockmanager.common.format.exception.webclient.errorCode;

import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum WebClientErrorCode implements ErrorEnumCode {
    SEJONG_INCORRECT_INFORM("E001", "입력하신 정보를 확인해주세요");
    private final String code;
    private final String message;
}
