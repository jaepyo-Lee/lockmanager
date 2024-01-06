package com.ime.lockmanager.account.adapter.in;

import com.ime.lockmanager.account.adapter.in.res.AccountInfoResponse;
import com.ime.lockmanager.account.application.port.in.AccountUsecase;
import com.ime.lockmanager.account.application.port.in.res.AccountInfoResponseDto;
import com.ime.lockmanager.common.format.success.SuccessResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.user.prefix}")
public class AccountController {
    private final AccountUsecase accountUsecase;

    @ApiOperation(value = "계좌 조회 api",
            notes = "[일반용] 계좌 정보를 조회하는 api 입니다.")
    @GetMapping("/users/{userId}/majors/accounts")
    public SuccessResponse<AccountInfoResponse> findAccountInfo(@ApiIgnore Authentication authentication,
                                                                @PathVariable Long userId) {
        return new SuccessResponse(accountUsecase.findAccountInfo(userId).toResponse());
    }
}
