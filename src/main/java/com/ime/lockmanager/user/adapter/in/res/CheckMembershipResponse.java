package com.ime.lockmanager.user.adapter.in.res;

import com.ime.lockmanager.user.domain.UserTier;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckMembershipResponse {
    private UserTier userTier;
}
