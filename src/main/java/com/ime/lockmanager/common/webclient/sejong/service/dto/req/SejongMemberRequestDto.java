package com.ime.lockmanager.common.webclient.sejong.service.dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SejongMemberRequestDto {
    private String id;
    private String pw;

    @Builder
    public SejongMemberRequestDto(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }
}
