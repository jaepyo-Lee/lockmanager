package com.ime.lockmanager.major.adapter.in.major;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.major.adapter.in.major.req.CreateMajorRequest;
import com.ime.lockmanager.major.adapter.in.major.req.ModifyMajorNameReqeust;
import com.ime.lockmanager.major.adapter.in.major.res.CreateMajorResponse;
import com.ime.lockmanager.major.adapter.in.major.res.ModifyMajorNameResponse;
import com.ime.lockmanager.major.application.port.in.MajorUseCase;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.master.prefix}/majors")
public class MajorMasterController {
    private final MajorUseCase majorUseCase;

    @ApiOperation(value = "대표학과명 변경API[마스터]",notes = "로직 수정 예정 사용하지 말기")
    @PutMapping("/name")
    public SuccessResponse<ModifyMajorNameResponse> modifyMajor(@ApiIgnore Authentication authentication,
                                                                    @RequestBody ModifyMajorNameReqeust modifyMajorNameReqeust) {
        return new SuccessResponse(
                ModifyMajorNameResponse.fromResponseDto(
                        majorUseCase.modifyMajorName(
                                modifyMajorNameReqeust.toRequestDto(authentication.getName()))
                )
        );
    }

    @ApiOperation(value = "대표학과 생성API[마스터]")
    @PostMapping()
    public SuccessResponse<CreateMajorResponse> createMajor(@ApiIgnore Authentication authentication,
                                                            @RequestBody CreateMajorRequest createMajorRequest){
        return new SuccessResponse(
                majorUseCase.createMajor(createMajorRequest.toRequestDto())
        );
    }
}
