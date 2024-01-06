package com.ime.lockmanager.file.application.port.in.usecase;

import org.springframework.web.multipart.MultipartFile;

public interface FileAdminUseCase {
    void ParseMembershipExcelForUpdateUserDuesInfo(MultipartFile membershipFile,Long userId) throws Exception;
}

