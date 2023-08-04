package com.ime.lockmanager.user.adapter.in;

import com.ime.lockmanager.common.webclient.sejong.service.dto.req.SejongMemberRequestDto;
import lombok.Getter;

@Getter
public class SejongMemberRequest {
    private String id;
    private String pw;

    public SejongMemberRequestDto toRequest(){
        return SejongMemberRequestDto.builder()
                .id(this.id)
                .pw(this.pw)
                .build();
    }
}
