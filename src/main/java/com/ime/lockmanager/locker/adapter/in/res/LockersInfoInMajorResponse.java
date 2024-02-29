package com.ime.lockmanager.locker.adapter.in.res;

import com.ime.lockmanager.locker.adapter.in.res.dto.LockersInfoDto;
import com.ime.lockmanager.locker.adapter.in.res.dto.LockersInfoInMajorDto;
import com.ime.lockmanager.locker.domain.lockerdetail.dto.LockerDetailInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LockersInfoInMajorResponse {
    @Schema(description = "사물함과 칸들의 정보")
    private List<LockersInfoDto> lockersInfo;
}
