package com.ime.lockmanager.user.adapter.in.res;

import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.UserTier;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoAdminResponse {
    @Schema(description = "사물함 이름")
    private String lockerName;
    @Schema(description = "학생이름")
    private String studentName;
    @Schema(description = "학생의 학번")
    private String studentNum;
    @Schema(description = "학생회비 납부여부")
    private UserTier userTier;
    @Schema(description = "학생이 예약한 사물함 번호")
    private String lockerNum;
    @Schema(description = "학생의 pk값")
    private Long userId;
    @Schema(description = "관리자 여부")
    private Role role;
    @Schema(description = "학생의 재학 상태")
    private String status;
}
