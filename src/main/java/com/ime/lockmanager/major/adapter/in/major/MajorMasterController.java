package com.ime.lockmanager.major.adapter.in.major;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.major.adapter.in.major.dto.AllMajorInfo;
import com.ime.lockmanager.major.adapter.in.major.req.CreateMajorRequest;
import com.ime.lockmanager.major.adapter.in.major.req.ModifyMajorNameReqeust;
import com.ime.lockmanager.major.adapter.in.major.res.AllMajorInfoResponse;
import com.ime.lockmanager.major.adapter.in.major.res.CreateMajorResponse;
import com.ime.lockmanager.major.adapter.in.major.res.ModifyMajorNameResponse;
import com.ime.lockmanager.major.application.port.in.MajorUseCase;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.master.prefix}/majors")
public class MajorMasterController {
    private final MajorUseCase majorUseCase;

    @ApiOperation(value = "대표학과명 변경API[마스터]")
    @PutMapping("/{majorId}/name")
    public SuccessResponse<ModifyMajorNameResponse> modifyMajor(@ApiIgnore Authentication authentication,
                                                                @PathVariable Long majorId,
                                                                @Valid @RequestBody ModifyMajorNameReqeust modifyMajorNameReqeust) {
        return new SuccessResponse(
                ModifyMajorNameResponse.fromResponseDto(
                        majorUseCase.modifyMajorName(
                                modifyMajorNameReqeust.toRequestDto(majorId))
                )
        );
    }

    @ApiOperation(value = "대표학과 전체 조회 API[마스터]")
    @GetMapping("")
    public SuccessResponse<AllMajorInfoResponse> findAllMajor(@ApiIgnore Authentication authentication) {
        List<AllMajorInfo> allMajorInfos = majorUseCase.findAll().stream()
                .map(allMajorInfoResponseDto -> AllMajorInfo.builder()
                        .majorName(allMajorInfoResponseDto.getMajorName())
                        .majorId(allMajorInfoResponseDto.getMajorId())
                        .build())
                .collect(Collectors.toList());
        return new SuccessResponse(
                AllMajorInfoResponse.builder()
                        .majorInfoList(allMajorInfos)
                        .build()
        );
    }

    @ApiOperation(value = "대표학과 생성API[마스터]")
    @PostMapping()
    public SuccessResponse<CreateMajorResponse> createMajor(@ApiIgnore Authentication authentication,
                                                            @RequestBody CreateMajorRequest createMajorRequest) {
        return new SuccessResponse(
                majorUseCase.createMajor(createMajorRequest.toRequestDto())
        );
    }
}
