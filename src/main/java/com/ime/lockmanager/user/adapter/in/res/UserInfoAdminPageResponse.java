package com.ime.lockmanager.user.adapter.in.res;

import com.ime.lockmanager.user.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserInfoAdminPageResponse {
    @Schema(description = "페이지네이션 전체 페이지수")
    private int totalPage;
    @Schema(description = "페이지네이션 현재 페이지")
    private int currentPage;
    @Schema(description = "페이지네이션 현재 페이지의 데이터수")
    private int currentElementSize;
    @Schema(description = "학생의 사물함 및 학생 정보")
    private List<UserInfoAdminResponse> adminResponse;
}
