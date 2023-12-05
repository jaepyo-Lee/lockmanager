package com.ime.lockmanager.major.adapter.in.majordetail;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.major.adapter.in.majordetail.req.CreateMajorDetailRequest;
import com.ime.lockmanager.major.adapter.in.majordetail.res.CreateMajorDetailResponse;
import com.ime.lockmanager.major.application.port.in.MajorDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.admin.prefix}/majorDetail")
public class MajorDetailAdminController {
    private final MajorDetailUseCase majorDetailUseCase;

    @PostMapping()
    public SuccessResponse<CreateMajorDetailResponse> createMajorDetail(@ApiIgnore Authentication authentication,
                                                                        @RequestBody CreateMajorDetailRequest createMajorDetailRequest) {

        String savedMajorDetailName = majorDetailUseCase
                .createMajorDetail(authentication.getName(), createMajorDetailRequest.toRequestDto());

        return new SuccessResponse(CreateMajorDetailResponse.builder()
                .savedMjorDetailName(savedMajorDetailName)
                .build());
    }
}
