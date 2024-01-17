package com.ime.lockmanager.major.adapter.in.majordetail.res;

import com.ime.lockmanager.major.application.port.in.res.MajorDetailInMajorResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "학과에 속하는 소속학과 전체 조회 응답 DTO")
@Builder
@Getter
public class MajorDetailsInMajorResponse {
    @Schema(description = "소속학과 정보리스트 DTO")
    List<MajorDetailInMajorResponseDto> majordetails;
    public static MajorDetailsInMajorResponse of(List<MajorDetailInMajorResponseDto> majordetails){
        return MajorDetailsInMajorResponse.builder()
                .majordetails(majordetails)
                .build();
    }
}
