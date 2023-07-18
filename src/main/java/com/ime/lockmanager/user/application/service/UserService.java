package com.ime.lockmanager.user.application.service;

import com.ime.lockmanager.common.exception.user.NotFoundUserException;
import com.ime.lockmanager.user.adapter.out.UserQueryRepository;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.application.port.in.req.ChangePasswordRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto.UserInfoResponseDtoBuilder;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserUseCase {
    private final UserQueryRepository userQueryRepository;
    @Override
    public UserInfoResponseDto findUserInfo(UserInfoRequestDto userRequestDto){
        User byStudentNum = userQueryRepository.findByStudentNum(userRequestDto.getStudentNum())
                .orElseThrow(NotFoundUserException::new);
        UserInfoResponseDtoBuilder build = UserInfoResponseDto.builder()
                .userNum(byStudentNum.getStudentNum())
                .userName(byStudentNum.getName())
                .membership(byStudentNum.isMembership());
        // null처리 안해주면 NullPointException뜸 db의 값이 없으면 자동으로 null반환되서 들어갈줄 알았는데 안됨
        if(byStudentNum.getLocker()!=null){
            UserInfoResponseDto userInfoResponseDto = build
                    .lockerNum(byStudentNum.getLocker().getId())
                    .build();
            return userInfoResponseDto;
        }
        UserInfoResponseDto userInfoResponseDto = build.build();
        return userInfoResponseDto;
    }

    @Transactional
    @Override
    public void changePassword(ChangePasswordRequestDto changePasswordRequestDto) {
        User byStudentNum = userQueryRepository.findByStudentNum(changePasswordRequestDto.getStudentNum())
                .orElseThrow(NotFoundUserException::new);
        byStudentNum.changePassword(changePasswordRequestDto.getNewPassword());
    }
}
