package com.ime.lockmanager.file.application.port.in.usecase.req;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class SaveImageInfoRequestDto {
    private MultipartFile image;
}
