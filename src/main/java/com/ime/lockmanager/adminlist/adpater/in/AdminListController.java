package com.ime.lockmanager.adminlist.adpater.in;

import com.ime.lockmanager.adminlist.application.port.in.AdminListUseCase;
import com.ime.lockmanager.adminlist.application.port.in.res.AdminListResponse;
import com.ime.lockmanager.common.format.success.SuccessResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.awt.print.Pageable;
import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Deprecated
@RestController
@RequestMapping("/admin/api/list")
class AdminListController {

    private final AdminListUseCase adminListUseCase;

    @ApiOperation(
            value = "사용자 정보 조회 api",
            notes = "관리자 페이지에서 학과의 전체 학생의 정보조회에 사용"
    )
    @GetMapping("")
    public SuccessResponse<AdminListResponse> getadminlist(@ApiIgnore Principal principal){
        log.info("{} : 사용자 리스트 조회(관리자)",principal.getName());
        return new SuccessResponse(AdminListResponse.fromResponseDto(adminListUseCase.getAdminList()));
    }
}
