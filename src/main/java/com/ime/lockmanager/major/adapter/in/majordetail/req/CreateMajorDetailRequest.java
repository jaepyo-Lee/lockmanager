package com.ime.lockmanager.major.adapter.in.majordetail.req;

import com.ime.lockmanager.major.adapter.in.majordetail.res.MajorDetailsInMajorResponse;
import com.ime.lockmanager.major.application.port.in.req.CreateMajorDetailRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Schema(description = "소속 학과를 생성하기 위한 요청 DTO")
@Getter
public class CreateMajorDetailRequest {
    @NotBlank(message = "생성할 소속 학과명은 비어있지 못합니다.")
    @Schema(description = "생성할 소속 학과명")
    private String majorDetailName;

    public CreateMajorDetailRequestDto toRequestDto(Long majorId){
        return CreateMajorDetailRequestDto.builder()
                .majorDetailName(majorDetailName)
                .majorId(majorId)
                .build();
    }
}
