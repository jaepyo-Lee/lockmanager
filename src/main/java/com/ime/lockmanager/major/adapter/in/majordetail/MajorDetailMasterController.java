package com.ime.lockmanager.major.adapter.in.majordetail;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.major.adapter.in.majordetail.req.CreateMajorDetailRequest;
import com.ime.lockmanager.major.adapter.in.majordetail.res.CreateMajorDetailResponse;
import com.ime.lockmanager.major.adapter.in.majordetail.res.MajorDetailInMajorResponse;
import com.ime.lockmanager.major.application.port.in.MajorDetailUseCase;
import com.ime.lockmanager.major.application.port.in.res.MajorDetailInMajorResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.master.prefix}/majors/{majorId}/majorDetails")
public class MajorDetailMasterController {
    private final MajorDetailUseCase majorDetailUseCase;

    @ApiOperation(value = "대표학과에 속하는 소학과 생성api[마스터용]")
    @PostMapping()
    public SuccessResponse<CreateMajorDetailResponse> createMajorDetail(@ApiIgnore Authentication authentication,
                                                                        @PathVariable Long majorId,
                                                                        @RequestBody CreateMajorDetailRequest createMajorDetailRequest) {
        String savedMajorDetailName = majorDetailUseCase
                .createMajorDetail(createMajorDetailRequest.toRequestDto(majorId));
        return new SuccessResponse(CreateMajorDetailResponse.builder()
                .savedMjorDetailName(savedMajorDetailName)
                .build());
    }

    @ApiOperation(value = "대표학과에 속하는 소학과 전체조회")
    @GetMapping("")
    public SuccessResponse<MajorDetailInMajorResponse> findMajorDetailInMajor(@ApiIgnore Authentication authentication,
                                                                              @PathVariable Long majorId) {
        List<MajorDetailInMajorResponseDto> majorDetailInMajorResponseDtos = majorDetailUseCase.findAllByMajorId(majorId);
        return new SuccessResponse(
                MajorDetailInMajorResponse.builder()
                        .majordetails(majorDetailInMajorResponseDtos)
                        .build()
        );
    }

}
