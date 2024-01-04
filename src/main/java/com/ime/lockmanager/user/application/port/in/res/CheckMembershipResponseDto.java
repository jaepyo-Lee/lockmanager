package com.ime.lockmanager.user.application.port.in.res;

import com.ime.lockmanager.user.adapter.in.res.CheckMembershipResponse;
import com.ime.lockmanager.user.domain.MembershipState;
import lombok.Builder;
import lombok.Getter;

import javax.print.DocFlavor;

@Builder
public class CheckMembershipResponseDto {
    private MembershipState membershipState;

    public CheckMembershipResponse toResponse() {
        return CheckMembershipResponse.builder()
                .membershipState(membershipState)
                .build();
    }
}
