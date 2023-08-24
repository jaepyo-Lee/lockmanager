package com.ime.lockmanager.adminlist.adpater.in;

import com.ime.lockmanager.adminlist.application.port.in.AdminListUseCase;
import com.ime.lockmanager.adminlist.application.port.in.res.AdminListResponse;
import com.ime.lockmanager.common.format.success.SuccessResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/api/list")
class AdminListController {

    private final AdminListUseCase adminListUseCase;

    @ApiOperation(
            value = "수정 정보 리스트 조회",
            notes = "어드민 페이지에서 사용자의 정보를 수정하기위한 수정가능 목록들 조회 API(eg. 남은 사물함번호)"
    )
    @GetMapping("")
    public SuccessResponse getadminlist(){
        return new SuccessResponse(AdminListResponse.fromResponseDto(adminListUseCase.getAdminList()));
    }
}
