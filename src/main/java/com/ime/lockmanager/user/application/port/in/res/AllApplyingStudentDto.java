package com.ime.lockmanager.user.application.port.in.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AllApplyingStudentDto {
    private String studentNum;
    private String studentName;
}
