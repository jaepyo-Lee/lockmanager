package com.ime.lockmanager.user.adapter.in.req;

import com.ime.lockmanager.user.application.port.in.req.ModifiedUserInfoDto;
import com.ime.lockmanager.user.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ModifiedUserInfo {
    @NotBlank(message = "사용자의 학번은 공백허용되지 않습니다.")
    @Schema(description = "수정하고자 하는 사용자의 학번")
    private String studentNum;
    private Boolean admin;
    private Boolean membership;
    private Long lockerDetailId;

    public ModifiedUserInfoDto toDto(){
        return ModifiedUserInfoDto.builder()
                .lockerDetailId(lockerDetailId)
                .membership(membership)
                .admin(admin)
                .studentNum(studentNum)
                .build();
    }
}
