package com.ime.lockmanager.adminlist.application.port.in;

import com.ime.lockmanager.adminlist.application.port.in.res.AdminListResponseDto;
import org.springframework.stereotype.Component;

import java.awt.print.Pageable;
public interface AdminListUseCase {
    AdminListResponseDto getAdminList();
}
