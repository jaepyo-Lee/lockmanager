package com.ime.lockmanager.common.format.exception.major.majordetail.errorCode;

import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MajorDetailErrorCode implements ErrorEnumCode {
    DUPLICATED_MAJOR_DETAIL_ERROR("MDE001", "중복된 학과명이 존재합니다. 관리자에게 문의해주세요");
    private String code;
    private String message;
}
