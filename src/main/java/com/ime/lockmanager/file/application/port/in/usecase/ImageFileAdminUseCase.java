package com.ime.lockmanager.file.application.port.in.usecase;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageFileAdminUseCase {
    String saveImageToS3(MultipartFile image) throws IOException;

    void deleteImageToS3(String imageUrl) throws IOException;
}
