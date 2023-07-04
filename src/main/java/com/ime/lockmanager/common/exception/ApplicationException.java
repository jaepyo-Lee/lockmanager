package com.ime.lockmanager.common.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException{
    private ErrorEnumCode errorEnumCode;
    public ApplicationException(ErrorEnumCode errorEnumCode){
        super(errorEnumCode.getMessage());
        this.errorEnumCode = errorEnumCode;
    }
}
