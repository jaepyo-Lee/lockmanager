package com.ime.lockmanager.account.adapter.in;

import com.ime.lockmanager.account.adapter.in.req.ModifyAccountRequest;
import com.ime.lockmanager.account.application.port.in.AccountUsecase;
import com.ime.lockmanager.account.application.port.in.res.SaveOrModifyAccountResponseDto;
import com.ime.lockmanager.common.format.success.SuccessResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.admin.prefix}")
public class AccountAdminController {
    private final AccountUsecase accountUsecase;

    @ApiOperation(value = "계좌를 저장 및 수정 api",
            notes = "[관리자용] 계좌가 존재하지 않는다면 저장하고, 존재한다면 수정하는 api입니다.")
    @PostMapping("/majors/{majorId}/accounts")
    public SuccessResponse<SaveOrModifyAccountResponseDto> saveOrModifyAccountInfo(@ApiIgnore Authentication authentication,
                                                                                   @PathVariable Long majorId,
                                                                                   @Valid @RequestBody ModifyAccountRequest modifyAccountRequest) {
        return new SuccessResponse<>(accountUsecase.saveOrModifyAccountInfo(
                majorId, modifyAccountRequest.toRequestDto()));
    }
}
