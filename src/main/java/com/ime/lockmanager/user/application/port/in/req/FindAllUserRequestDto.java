package com.ime.lockmanager.user.application.port.in.req;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindAllUserRequestDto {
    private Long majorId;
    private String search;
    private int page;
    public static FindAllUserRequestDto of(Long majorId, String search, int page) {
        return FindAllUserRequestDto.builder()
                .majorId(majorId)
                .search(search)
                .page(page)
                .build();
    }
}
