package com.ime.lockmanager.locker.application.port.in.res;

import com.ime.lockmanager.locker.application.port.in.dto.LeftLockerInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "남은 사물함 목록 응답DTO")
public class LeftLockerResponse {
    private List<LeftLockerInfo> leftLockerInfo;

}
