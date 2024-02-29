package com.ime.lockmanager.user.domain.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateUserStatusInfoDto {
    private String status;

    // 학생회비 납부명단 업데이트시 사용자 저장하는 로직 작성예정으로 일단 필드 생성
    private String name;
    private String grade;
    private String major;
}
