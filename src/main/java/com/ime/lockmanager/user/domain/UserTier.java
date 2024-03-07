package com.ime.lockmanager.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserTier {
    MEMBER("MEMBER","납부자","승인이 완료되었습니다. 더 많은 혜택으로 보답드리겠습니다."),
    NON_MEMBER("NON_MEMBER","미납부자","더 많은 행사와 복지를 위해 학생회비 납부 부탁드립니다."),
    APPLICANT("APPLICANT","신청자","승인이 진행중입니다. 조금만 기다려주시면 감사하겠습니다.");
    private String name;
    private String krTier;
    private String msg;
    public static UserTier judge(boolean tier){
        if(tier){
            return MEMBER;
        }
        return NON_MEMBER;
    }
}
