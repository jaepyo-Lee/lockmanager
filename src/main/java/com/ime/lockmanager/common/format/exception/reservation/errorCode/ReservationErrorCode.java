package com.ime.lockmanager.common.format.exception.reservation.errorCode;

import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationErrorCode implements ErrorEnumCode {
    NOT_FOUND_RESERVATION("RE001", "해당 예약이 조회되지 않습니다")
    ,NOT_MATCH_USER_TIER_AND_LOCKER("RE002","사용자의 납부여부와 사물함의 설정이 일치하지 않습니다.");
    private String code;
    private String message;
}
