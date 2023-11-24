package com.ime.lockmanager.locker.domain.dto;


import com.ime.lockmanager.locker.domain.Locker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LockerDetailCreateDto {
    private Locker locker;
    private String lockerNum;
    private String rowNum;

    private String columnNum;

    private boolean isUsable;
}
