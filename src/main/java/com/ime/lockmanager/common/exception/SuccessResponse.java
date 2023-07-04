package com.ime.lockmanager.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static com.ime.lockmanager.common.exception.SuccessResponseStatus.SUCCESS;
import static java.time.LocalDateTime.now;

@JsonPropertyOrder({"isSuccess","code","message","result"})
public class SuccessResponse<T> {
    @JsonProperty("status")
    private int status;
    private LocalDateTime time;
    private String code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    //성공의 경우
    public SuccessResponse(T result){
        this.status= HttpStatus.OK.value();
        this.time = now();
        this.code = SUCCESS.getCode();
        this.message = SUCCESS.getMessage();
        this.result = result;
    }
}
