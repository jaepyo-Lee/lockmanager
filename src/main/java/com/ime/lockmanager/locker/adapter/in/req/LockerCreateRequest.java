package com.ime.lockmanager.locker.adapter.in.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "사물함 생성시 요청 DTO")
@Getter
public class LockerCreateRequest {
    @Schema(description = "생성할 사물함 이름")
    private String lockerName;
    @Schema(description = "생성할 사물함의 전체 행 개수")
    private String totalRow;
    @Schema(description = "생성할 사물함의 전체 열 개수")
    private String totalColumn;
    @Schema(description = "생성할 사물함의 사진이름, 해당 정보는 이미지 저장시 제공할 예정")
    private String imageName;
    @Schema(description = "생성할 사물함의 사진 URL, 해당 정보는 이미지 저장시 제공할 예정")
    private String imageUrl;
    @Schema(description = "생성할 사물함의 예약 시작 시간")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startReservationTime;

    @Schema(description = "생성할 사물함의 예약 마감 시간")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endReservationTime;

    @Schema(description = "생성할 사물함의 각 칸 정보")
    private List<LockerDetailCreateRequest> lockerDetailCreateRequests;
}
