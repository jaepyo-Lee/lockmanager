package com.ime.lockmanager.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum UserState {
    attend("재학"),rest("휴학"),graduate("졸업");
    private String krName;
}
