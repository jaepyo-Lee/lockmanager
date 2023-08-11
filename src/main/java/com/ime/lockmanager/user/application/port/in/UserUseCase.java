package com.ime.lockmanager.user.application.port.in;

import com.ime.lockmanager.user.application.port.in.req.ChangePasswordRequestDto;
import com.ime.lockmanager.user.application.port.in.req.ModifiedUserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserCancelLockerRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.res.UserCancelLockerResponseDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;

import java.util.List;

public interface UserUseCase {
    UserInfoResponseDto findUserInfo(UserInfoRequestDto userRequestDto);

    List<UserInfoResponseDto> findAllUserInfo();

    boolean checkAdmin(String studentNum);

    void cancelLocker(UserCancelLockerRequestDto cancelLockerDto);

    void modifiedUserInfo(List<ModifiedUserInfoRequestDto> requestDto) throws Exception;
}
