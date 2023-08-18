package com.ime.lockmanager.common.webclient.sejong.service.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SejongMemberResponseDto {
    private String msg;
    private SejongMemberResponseResult result;
}
