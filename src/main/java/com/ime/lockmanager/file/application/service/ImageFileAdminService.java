package com.ime.lockmanager.file.application.service;

import com.ime.lockmanager.common.util.S3Operator;
import com.ime.lockmanager.file.application.port.in.usecase.ImageFileAdminUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ImageFileAdminService implements ImageFileAdminUseCase {
    private final S3Operator s3Operator;
    private static final String DIR_NAME = "SEJONG_BUCKET";
    @Override
    public String saveImageToS3(MultipartFile image) throws IOException {
        return s3Operator.upload(image, DIR_NAME);
    }

    @Override
    public void deleteImageToS3(String imageUrl) throws IOException {
        s3Operator.deleteFile(imageUrl);
    }
}
