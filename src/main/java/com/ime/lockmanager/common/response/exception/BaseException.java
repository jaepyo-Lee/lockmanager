package com.ime.lockmanager.common.response.exception;

import com.ime.lockmanager.common.response.BaseResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BaseException extends Exception{
    private final BaseResponseStatus status;
}
