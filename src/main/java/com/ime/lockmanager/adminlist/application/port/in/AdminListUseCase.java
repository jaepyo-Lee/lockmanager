package com.ime.lockmanager.adminlist.application.port.in;

import com.ime.lockmanager.adminlist.application.port.in.res.AdminListResponseDto;

public interface AdminListUseCase {
    AdminListResponseDto getAdminList();
}
