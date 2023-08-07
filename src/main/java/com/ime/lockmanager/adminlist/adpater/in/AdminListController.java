package com.ime.lockmanager.adminlist.adpater.in;

import com.ime.lockmanager.adminlist.application.port.in.AdminListUseCase;
import com.ime.lockmanager.adminlist.application.port.in.res.AdminListResponse;
import com.ime.lockmanager.common.format.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/api/list")
public class AdminListController {

    private final AdminListUseCase adminListUseCase;

    @GetMapping("")
    public SuccessResponse getadminlist(){
        return new SuccessResponse(AdminListResponse.fromResponseDto(adminListUseCase.getAdminList()));
    }
}
