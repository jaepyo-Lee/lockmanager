package com.ime.lockmanager.common.format.success;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@ToString
@Getter
@AllArgsConstructor
@JsonPropertyOrder({"time", "status", "code", "message", "result"})
public class SuccessResponse<T> {
    @JsonProperty("status")
    private int status;
    private LocalDateTime time;
    private String code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    @Builder
    public SuccessResponse(int status, LocalDateTime time, String code, String message) {
        this.status = status;
        this.time = time;
        this.code = code;
        this.message = message;
    }

    //성공의 경우
    public SuccessResponse(T result) {
        this.status = HttpStatus.OK.value();
        this.time = now();
        this.code = SuccessResponseStatus.SUCCESS.getCode();
        this.message = SuccessResponseStatus.SUCCESS.getMessage();
        this.result = result;
    }

    public SuccessResponse(SuccessResponseStatus successResponseStatus) {
        this.status = HttpStatus.OK.value();
        this.time = now();
        this.code = successResponseStatus.getCode();
        this.message = successResponseStatus.getMessage();
    }

    public SuccessResponse(T result, SuccessResponseStatus successResponseStatus) {
        this.status = HttpStatus.OK.value();
        this.time = now();
        this.code = successResponseStatus.getCode();
        this.message = successResponseStatus.getMessage();
        this.result = result;
    }

    public static SuccessResponse ok(String message) {
        return SuccessResponse.builder()
                .status(HttpStatus.OK.value())
                .time(now())
                .code(SuccessResponseStatus.SUCCESS.getCode())
                .message(message)
                .build();
    }

    public static SuccessResponse ok() {
        return SuccessResponse.builder()
                .status(HttpStatus.OK.value())
                .time(now())
                .code(SuccessResponseStatus.SUCCESS.getCode())
                .message("SUCCESS")
                .build();
    }
}
