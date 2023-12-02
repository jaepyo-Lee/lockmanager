package com.ime.lockmanager.user.adapter.in.res;

import com.ime.lockmanager.user.domain.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserInfoAdminPageResponse {
    private int totalPage;
    private int currentPage;
    private int currentElementSize;
    private List<UserInfoAdminResponse> adminResponse;
}
