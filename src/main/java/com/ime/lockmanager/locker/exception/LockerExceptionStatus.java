package com.ime.lockmanager.locker.exception;

import lombok.Getter;

@Getter
public enum LockerExceptionStatus{
    NOT_EXIST_LOCKER(false,2000,"존재하지 않는 사물함입니다."),
    ALREADY_RESERVED_LOCKER(false, 2001, "이미 예약된 사물함입니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;
    private LockerExceptionStatus(boolean isSuccess,int code,String message){
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
