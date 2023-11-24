package com.ime.lockmanager.locker.adapter.in.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LockerCreateResponse {
    private String createdLockerName;
}
