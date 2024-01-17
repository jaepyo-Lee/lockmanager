package com.ime.lockmanager.major.adapter.in.major.res;

import com.ime.lockmanager.major.adapter.in.major.dto.AllMajorInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "학과 전체 조회 응답 DTO")
@Builder
@Getter
public class AllMajorInfoResponse {
    @Schema(description = "조회된 학과 리스트")
    List<AllMajorInfo> majorInfoList;
}
