package com.ime.lockmanager.common.webclient.sejong.service.dto.res;

import com.ime.lockmanager.common.webclient.sejong.service.dto.res.SejongMemberResponseBody;
import lombok.Getter;

@Getter
public class SejongMemberResponseResult {
    private SejongMemberResponseBody body;
    private String is_auth;
}
