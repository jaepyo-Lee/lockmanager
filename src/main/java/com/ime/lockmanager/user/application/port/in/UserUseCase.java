package com.ime.lockmanager.user.application.port.in;

import com.ime.lockmanager.user.application.port.in.dto.UpdateUserDueInfoDto;
import com.ime.lockmanager.user.application.port.in.req.DetermineApplyingRequestDto;
import com.ime.lockmanager.user.application.port.in.req.ModifiedUserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.res.AllApplyingStudentPageResponseDto;
import com.ime.lockmanager.user.application.port.in.res.CheckMembershipResponseDto;
import com.ime.lockmanager.user.application.port.in.res.UserTierResponseDto;
import com.ime.lockmanager.user.application.port.out.res.AllUserInfoForAdminResponseDto;
import com.ime.lockmanager.user.application.port.out.res.UserInfoQueryResponseDto;
import com.ime.lockmanager.user.domain.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserUseCase {
    UserInfoQueryResponseDto findUserInfoByStudentNum(UserInfoRequestDto userRequestDto);

    Page<AllUserInfoForAdminResponseDto> findAllUserInfo(String adminUserStudentNum,int page);

    void modifiedUserInfo(ModifiedUserInfoRequestDto requestDto) throws Exception;

    void updateUserDueInfoOrSave(UpdateUserDueInfoDto updateUserDueInfoDto) throws Exception;

    User findByStudentNum(String studentNum);

    Optional<User> findByStudentNumWithMajorDetailWithMajor(String studentNum);

    UserTierResponseDto applyMembership(Long userId);

    AllApplyingStudentPageResponseDto findAllApplying(String studentNum, int page);

    UserTierResponseDto determineApplying(DetermineApplyingRequestDto toRequestDto, boolean isApprove);

    CheckMembershipResponseDto checkMembership(Long userId);
}
