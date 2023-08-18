package com.ime.lockmanager.common.webclient.sejong.service.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SejongMemberResponseBody {
    private String name;
    private String grade;
    private String status;
    private String message;
    private String major;
}
