package com.ime.lockmanager.file.adapter.in.req;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ParseDuesExcelRequest {
    private MultipartFile file;
}
