package com.ime.lockmanager.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import static com.ime.lockmanager.common.response.BaseResponseStatus.SUCCESS;

@JsonPropertyOrder({"isSuccess","code","message","result"})
public class BaseResponse<T> {
    @JsonProperty("isSuccess")
    private boolean isSuccess;
    private int code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    //성공의 경우
    public BaseResponse(T result){
        this.isSuccess= SUCCESS.isSuccess();
        this.code = SUCCESS.getCode();
        this.message = SUCCESS.getMessage();
        this.result = result;
    }
}
