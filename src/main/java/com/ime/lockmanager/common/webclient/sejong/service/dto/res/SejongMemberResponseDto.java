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

    public UpdateUserStatusInfoDto toUpdateUserStatusInfoDto(){
        return UpdateUserStatusInfoDto.builder()
                .status(this.result.getBody().getStatus())
                .grade(this.result.getBody().getGrade())
                .major(this.result.getBody().getMajor())
                .name(this.result.getBody().getName())
                .build();
    }

    public UpdateUserInfoDto toUpdateUserInfoDto() {
        return UpdateUserInfoDto.builder()
                .auth(true)
                .grade(this.result.getBody().getGrade())
                .major(this.result.getBody().getMajor())
                .status(this.result.getBody().getStatus())
                .build();
    }
}
