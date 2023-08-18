package com.ime.lockmanager.common.webclient.sejong.service.dto.res;

import com.ime.lockmanager.common.webclient.sejong.service.dto.res.SejongMemberResponseBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SejongMemberResponseResult {
    private SejongMemberResponseBody body;
    private String is_auth;
}
