package com.ime.lockmanager.user.application.port.in;

import com.ime.lockmanager.user.application.port.in.dto.UpdateUserDueInfoDto;
import com.ime.lockmanager.user.application.port.in.req.ModifiedUserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.out.res.AllUserInfoForAdminResponseDto;
import com.ime.lockmanager.user.application.port.out.res.UserInfoResponseDto;
import com.ime.lockmanager.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserUseCase {
    UserInfoResponseDto findUserInfoByStudentNum(UserInfoRequestDto userRequestDto);

    Page<AllUserInfoForAdminResponseDto> findAllUserInfo(String adminUserStudentNum, Pageable pageable);

    boolean checkAdmin(String studentNum);

    void modifiedUserInfo(ModifiedUserInfoRequestDto requestDto) throws Exception;

    void updateUserDueInfoOrSave(UpdateUserDueInfoDto updateUserDueInfoDto) throws Exception;

    User findByStudentNum(String studentNum);

    Optional<User> findByStudentNumWithMajorDetailWithMajor(String studentNum);
}
