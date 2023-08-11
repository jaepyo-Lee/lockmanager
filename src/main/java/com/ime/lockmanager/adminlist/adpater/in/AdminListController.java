package com.ime.lockmanager.adminlist.adpater.in;

import com.ime.lockmanager.adminlist.application.port.in.AdminListUseCase;
import com.ime.lockmanager.adminlist.application.port.in.res.AdminListResponse;
import com.ime.lockmanager.common.format.success.SuccessResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/api/list")
public class AdminListController {

    private final AdminListUseCase adminListUseCase;

    @ApiOperation(
            value = "사용자 정보 조회",
            notes = "어드민 페이지에서 사용자의 정보를 수정하기 위한 모든 사용자 정보조회 API"
    )
    @GetMapping("")
    public SuccessResponse getadminlist(){
        return new SuccessResponse(AdminListResponse.fromResponseDto(adminListUseCase.getAdminList()));
    }
}
