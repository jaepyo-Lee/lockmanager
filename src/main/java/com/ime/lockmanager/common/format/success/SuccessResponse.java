package com.ime.lockmanager.common.format.success;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse<T> {
    @JsonProperty("status")
    private String status;
    private LocalDateTime time;
    private int code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    //성공의 경우
    public SuccessResponse(T result){
        this.status=SuccessResponseStatus.SUCCESS.getCode();
        this.time = now();
        this.code = HttpStatus.OK.value();
        this.message = SuccessResponseStatus.SUCCESS.getMessage();
        this.result = result;
    }
}
