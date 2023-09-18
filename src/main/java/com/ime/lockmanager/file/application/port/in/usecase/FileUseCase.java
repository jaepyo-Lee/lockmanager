package com.ime.lockmanager.file.application.port.in.usecase;

import org.springframework.web.multipart.MultipartFile;

public interface FileUseCase {
    void ParseMembershipExcelForUpdateUserDuesInfo(MultipartFile membershipFile) throws Exception;
}
