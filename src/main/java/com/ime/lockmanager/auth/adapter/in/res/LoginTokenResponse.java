package com.ime.lockmanager.auth.adapter.in.res;

import com.ime.lockmanager.user.domain.Role;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginTokenResponse {
    private Role role;
    private Long userId;
    private Long majorId;
    private String accessToken;
    private String refreshToken;
}
