package com.ime.lockmanager.user.application.port.in;

import com.ime.lockmanager.user.application.port.in.req.ChangePasswordRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;

import java.util.List;

public interface UserUseCase {
    UserInfoResponseDto findUserInfo(UserInfoRequestDto userRequestDto);

    List<UserInfoResponseDto> findAllUserInfo();

    void changePassword(ChangePasswordRequestDto changePasswordRequestDto);

    boolean checkAdmin(String studentNum);
}
