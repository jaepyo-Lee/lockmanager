package com.ime.lockmanager.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserTier {
    MEMBER("납부자"),NON_MEMBER("미납부자");
    private String krTier;
}
