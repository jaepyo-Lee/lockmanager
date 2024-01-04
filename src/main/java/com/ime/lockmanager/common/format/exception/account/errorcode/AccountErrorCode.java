package com.ime.lockmanager.common.format.exception.account.errorcode;

import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountErrorCode implements ErrorEnumCode {
    NOT_FOUND_ACCOUNT("ACE001","학생회 계좌 정보를 찾지 못하였습니다.");
    private final String code;
    private final String message;
}
