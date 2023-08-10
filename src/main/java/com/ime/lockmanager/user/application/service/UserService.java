package com.ime.lockmanager.user.application.service;

import com.ime.lockmanager.common.format.exception.locker.InvalidCancelLockerException;
import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.user.adapter.out.UserQueryRepository;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.application.port.in.req.ChangePasswordRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserCancelLockerRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.res.UserCancelLockerResponseDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto.UserInfoResponseDtoBuilder;
import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserService implements UserUseCase {
    private final UserQueryRepository userQueryRepository;


    @Override
    public void cancelLocker(UserCancelLockerRequestDto cancelLockerDto) {
        User byStudentNum = userQueryRepository.findByStudentNum(cancelLockerDto.getStudentNum())
                .orElseThrow(NotFoundUserException::new);
        if(byStudentNum.getLocker()==null){
            throw new InvalidCancelLockerException();
        }
        log.info("{} : 사물함 취소",cancelLockerDto.getStudentNum());
        byStudentNum.cancelLocker();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserInfoResponseDto> findAllUserInfo() {
        List<User> all = userQueryRepository.findAll();
        List<UserInfoResponseDto> userInfoResponseDtos=new ArrayList<>();
        for (User user : all) {
            userInfoResponseDtos.add(getUserInfoResponseDto(user));
        }
        return userInfoResponseDtos;

    }

    @Transactional(readOnly = true)
    @Override
    public UserInfoResponseDto findUserInfo(UserInfoRequestDto userRequestDto){
        User byStudentNum = userQueryRepository.findByStudentNum(userRequestDto.getStudentNum())
                .orElseThrow(NotFoundUserException::new);
        return getUserInfoResponseDto(byStudentNum);
    }

    private static UserInfoResponseDto getUserInfoResponseDto(User user) {
        UserInfoResponseDtoBuilder build = UserInfoResponseDto.builder()
                .studentNum(user.getStudentNum())
                .userName(user.getName())
                .membership(user.isMembership())
                .role(user.getRole())
                .status(user.getStatus());
        // null처리 안해주면 getId를 못하니까 NullPointException뜸
        if(user.getLocker()!=null){
            UserInfoResponseDto userInfoResponseDto = build
                    .lockerNum(user.getLocker().getId())
                    .build();
            return userInfoResponseDto;
        }
        UserInfoResponseDto userInfoResponseDto = build.build();
        return userInfoResponseDto;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean checkAdmin(String studentNum) {
        User byStudentNum = userQueryRepository.findByStudentNum(studentNum)
                .orElseThrow(NotFoundUserException::new);
        if(byStudentNum.getRole()== Role.ROLE_ADMIN){
            return true;
        }else{
            return false;
        }
    }
}
