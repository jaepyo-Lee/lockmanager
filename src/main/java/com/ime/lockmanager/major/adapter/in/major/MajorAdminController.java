package com.ime.lockmanager.major.adapter.in.major;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.major.adapter.in.major.req.ModifyMajorNameReqeust;
import com.ime.lockmanager.major.adapter.in.major.res.ModifyMajorNameResponse;
import com.ime.lockmanager.major.application.port.in.MajorUseCase;
import com.ime.lockmanager.major.application.port.in.res.ModifyMajorNameResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.admin.prefix}/major")
public class MajorAdminController {
    private final MajorUseCase majorUseCase;

    @PutMapping("/name")
    public SuccessResponse<ModifyMajorNameResponse> modifyMajorName(@ApiIgnore Authentication authentication,
                                                                    @RequestBody ModifyMajorNameReqeust modifyMajorNameReqeust) {

        return new SuccessResponse(
                ModifyMajorNameResponse.fromResponseDto(
                        majorUseCase.modifyMajorName(
                                modifyMajorNameReqeust.toRequestDto(authentication.getName()))
                )
        );

    }
}
