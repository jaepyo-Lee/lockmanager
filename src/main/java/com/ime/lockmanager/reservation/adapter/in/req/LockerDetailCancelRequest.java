package com.ime.lockmanager.reservation.adapter.in.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LockerDetailCancelRequest {
    @Schema(description = "취소하는 학생의 PK (userId)")
    @NotBlank
    private Long userId;
}
