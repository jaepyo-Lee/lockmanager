package com.ime.lockmanager.common.format.exception.locker.errorCode;

import com.ime.lockmanager.common.format.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LockerErrorCode implements ErrorEnumCode {
    NOT_FOUND_LOCKER("LE001","사물함을 찾지 못했습니다."),
    ALREADY_RESERVERD_LOCKER("LE002","이미 예약된 사물함입니다."),
    IS_NOT_RESERVED_TIME("LE003","예약 가능한 시간이 아닙니다."),
    RESESRVE_TIME_NULL("LE004","예약 가능 시간이 설정되지 않았습니다."),
    INVALID_CANCEL_LOCKER("LE005","취소할수 있는 사물함이 없습니다."),
    INVALID_LOCKER_DETAIL("LE006","유효하지 않은 사물함칸입니다. 없는사물함칸입니다.");
    private final String code;
    private final String message;
}
