package com.ime.lockmanager.user.application.port.in;

import com.ime.lockmanager.user.application.port.in.req.ModifiedUserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserCancelLockerRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserUseCase {
    UserInfoResponseDto findUserInfoByStudentNum(UserInfoRequestDto userRequestDto);

    Page<UserInfoResponseDto> findAllUserInfo(Pageable pageable);

    boolean checkAdmin(String studentNum);

    void cancelLockerByStudentNum(UserCancelLockerRequestDto cancelLockerDto);

    void modifiedUserInfo(List<ModifiedUserInfoRequestDto> requestDto) throws Exception;
}
