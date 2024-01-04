package com.ime.lockmanager.user.adapter.in;

import com.ime.lockmanager.user.application.port.in.res.AllApplyingStudentDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AllApplyingStudentPageResponse {
    private int currentPage;
    private int totalPage;
    private List<AllApplyingStudentDto> applicant;
}
