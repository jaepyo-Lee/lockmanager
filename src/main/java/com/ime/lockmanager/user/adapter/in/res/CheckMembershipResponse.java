package com.ime.lockmanager.user.adapter.in.res;

import com.ime.lockmanager.user.domain.MembershipState;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckMembershipResponse {
    private MembershipState membershipState;
}
