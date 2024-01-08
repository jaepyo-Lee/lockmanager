package com.ime.lockmanager.file.application.port.in.usecase.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SaveImageInfoResponseDto {
    private String url;
}
