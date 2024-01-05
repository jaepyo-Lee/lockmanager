package com.ime.lockmanager.user.application.port.in.res;

import com.ime.lockmanager.user.adapter.in.res.UserTierResponse;
import com.ime.lockmanager.user.domain.UserTier;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserTierResponseDto {
    private UserTier userTier;

    public UserTierResponse toResponse() {
        return UserTierResponse.builder()
                .userTier(userTier)
                .build();
    }
}
