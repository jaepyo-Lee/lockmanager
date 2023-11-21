package com.ime.lockmanager.common.webclient.sejong.service.dto.res;

import com.ime.lockmanager.user.domain.dto.UpdateUserInfoDto;
import com.ime.lockmanager.user.domain.response.UpdateUserStatusInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SejongMemberResponseDto {
    private String msg;
    private SejongMemberResponseResult result;

    public UpdateUserInfoDto toUpdateUserInfoDto() {
        return UpdateUserInfoDto.builder()
                .auth(true)
                .grade(this.result.getBody().getGrade())
                .status(this.result.getBody().getStatus())
                .build();
    }
}
