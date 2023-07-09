package com.ime.lockmanager.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;

@Getter
public class ErrorResponse {
    private LocalDateTime time;
    private int status;
    private String code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FieldError> errors;


    private ErrorResponse(ApplicationRunException e){
        this.time = now();
        this.status = HttpStatus.BAD_REQUEST.value();
        this.code = e.getErrorEnumCode().getCode();
        this.message = e.getMessage();
    }

    public static ErrorResponse of(ApplicationRunException e) {
        return new ErrorResponse(e);
    }
}
