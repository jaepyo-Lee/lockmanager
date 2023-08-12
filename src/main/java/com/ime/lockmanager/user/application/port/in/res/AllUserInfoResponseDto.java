package com.ime.lockmanager.user.application.port.in.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AllUserInfoResponseDto {
    List<UserInfoResponseDto> infoResponseDtos;

    @Builder
    public AllUserInfoResponseDto(List<UserInfoResponseDto> infoResponseDtos) {
        this.infoResponseDtos = infoResponseDtos;
    }
}
