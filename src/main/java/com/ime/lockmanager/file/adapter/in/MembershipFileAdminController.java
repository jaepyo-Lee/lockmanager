package com.ime.lockmanager.file.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.file.application.port.in.usecase.MembershipFileAdminUseCase;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("${api.admin.prefix}")
@RestController
public class MembershipFileAdminController {
    private final MembershipFileAdminUseCase membershipFileAdminUseCase;

    @ApiOperation(
            value = "학생회비 엑셀파일을 업로드시 학생들의 학생회비 납부여부 업데이트",
            notes = "학생회비 엑셀파일을 업로드시 학생들의 학생회비 납부여부를 업데이트해주는 api(관리자용)."
    )
    @PostMapping(value = "/users/{userId}/file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse ParseDuesExcelForUpdateUserDuesInfo(
            @Parameter(name = "membershipFile", description = "학생회비 납부명단 엑셀파일", in = ParameterIn.QUERY)
            @RequestPart MultipartFile membershipFile,
            @PathVariable Long userId
    ) throws Exception {
        membershipFileAdminUseCase.ParseMembershipExcelForUpdateUserDuesInfo(membershipFile,userId);
        return SuccessResponse.ok();
    }

}
