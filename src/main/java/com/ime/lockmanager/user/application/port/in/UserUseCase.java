package com.ime.lockmanager.user.application.port.in;

import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;

public interface UserUseCase {
    UserInfoResponseDto findUserInfo(UserInfoRequestDto userRequestDto);
}
