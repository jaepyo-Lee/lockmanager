package com.ime.lockmanager.common.format.success;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessResponseStatus {
    SUCCESS("Success", "요청에 성공하였습니다."),
    SUCCESS_LOGOUT("AS001","로그아웃에 성공하였습니다"),
    SUCCESS_LOGIN("AS002","로그인에 성공하였습니다"),
    SUCCESTT_REISSUE_TOKEN("AS003","토큰 재발급에 성공하였습니다");

    private final String code;
    private final String message;

}
