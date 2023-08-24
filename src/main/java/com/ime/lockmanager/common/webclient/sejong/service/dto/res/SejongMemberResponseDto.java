package com.ime.lockmanager.common.webclient.sejong.service.dto.res;

import com.ime.lockmanager.user.domain.response.UpdateUserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SejongMemberResponseDto {
    private String msg;
    private SejongMemberResponseResult result;

    public UpdateUserInfoDto toUpdateUserInfoDto(){
        return UpdateUserInfoDto.builder()
                .status(this.result.getBody().getStatus())
                .build();
    }
}
