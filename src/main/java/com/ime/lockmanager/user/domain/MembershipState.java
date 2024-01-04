package com.ime.lockmanager.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MembershipState {
    DENY("거절","승인이 거절되었습니다. 자세한 내용은 학생회에 문의부탁드립니다."),
    APPROVE("승인","승인이 완료되었습니다. 더 많은 혜택으로 보답드리겠습니다."),
    APPLYING("진행중","승인이 진행중입니다."),
    NORMAL("신청 안함","더 많은 행사와 복지를 위해 학생회비 납부 부탁드립니다.");

    private String krState;
    private String msg;
}
