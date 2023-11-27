package com.ime.lockmanager.auth.application.port.in.req;

import com.ime.lockmanager.common.webclient.sejong.service.dto.req.SejongMemberRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    private String id;
    private String pw;

    public SejongMemberRequestDto toSejongMemberDto() {
        return SejongMemberRequestDto.builder()
                .id(id)
                .pw(pw)
                .build();
    }

}
