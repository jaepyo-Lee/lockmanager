package com.ime.lockmanager.user.application.port.in;

import com.ime.lockmanager.user.application.port.in.dto.UpdateUserDueInfoDto;
import com.ime.lockmanager.user.application.port.in.req.ModifiedUserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;
import com.ime.lockmanager.user.application.port.out.res.UserInfoForAdminModifiedPageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserUseCase {
    UserInfoResponseDto findUserInfoByStudentNum(UserInfoRequestDto userRequestDto);

    Page<UserInfoForAdminModifiedPageResponseDto> findAllUserInfo(Pageable pageable);

    boolean checkAdmin(String studentNum);

    void modifiedUserInfo(List<ModifiedUserInfoRequestDto> requestDto) throws Exception;

    void updateUserDueInfoOrSave(UpdateUserDueInfoDto updateUserDueInfoDto) throws Exception;
}
