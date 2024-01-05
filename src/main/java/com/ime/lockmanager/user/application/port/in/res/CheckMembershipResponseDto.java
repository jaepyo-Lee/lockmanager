package com.ime.lockmanager.user.application.port.in.res;

import com.ime.lockmanager.user.adapter.in.res.CheckMembershipResponse;
import com.ime.lockmanager.user.domain.MembershipState;
import com.ime.lockmanager.user.domain.UserTier;
import lombok.Builder;

@Builder
public class CheckMembershipResponseDto {
    private UserTier userTier;

    public CheckMembershipResponse toResponse() {
        return CheckMembershipResponse.builder()
                .userTier(userTier)
                .build();
    }
}
