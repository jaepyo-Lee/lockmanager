package com.ime.lockmanager.locker.adapter.in.res;

import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "사물함 예약시 응답DTO")
@Getter
@Builder
public class LockerRegisterResponse {
    @Schema(description = "예약한 사물함 칸 번호")
    private String lockerDetailNum;
    @Schema(description = "예약한 학생의 학번")
    private String studentNum;
    @Schema(description = "예약한 사물함 이름")
    private String lockerName;
    public static LockerRegisterResponse fromResponse(LockerRegisterResponseDto lockerRegisterResponseDto) {
        return LockerRegisterResponse.builder()
                .lockerDetailNum(lockerRegisterResponseDto.getLockerDetailNum())
                .lockerName(lockerRegisterResponseDto.getLockerName())
                .studentNum(lockerRegisterResponseDto.getStudentNum())
                .build();
    }
}
