package com.ime.lockmanager.common.format.exception.file.errorCode;

import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileErrorCode implements ErrorEnumCode {
    NOT_VALID_EXCEL_FORMAT("F001",".xls, .xlsx 형식의 파일이 아닙니다."),
    NOT_VALID_DUES_CHECKING("F002","학생회비 납부여부의 올바르지 않은 표현이 포함되어있습니다. 문서를 확인해주세요.");
    private String code;
    private String message;
}
