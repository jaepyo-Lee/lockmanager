package com.ime.lockmanager.major.adapter.in.majordetail.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "생성된 소속 학과의 응답 DTO")
@Getter
@Builder
public class CreateMajorDetailResponse {
    @Schema(description = "생성된 소속 학과의 이름")
    private String savedMjorDetailName;
    public static CreateMajorDetailResponse of(String savedMjorDetailName){
        return CreateMajorDetailResponse.builder()
                .savedMjorDetailName(savedMjorDetailName)
                .build();
    }
}
