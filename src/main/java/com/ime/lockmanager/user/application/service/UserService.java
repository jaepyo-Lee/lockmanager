package com.ime.lockmanager.user.application.service;

import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.user.adapter.out.UserQueryRepository;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.application.port.in.req.ChangePasswordRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto.UserInfoResponseDtoBuilder;
import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements UserUseCase {
    private final UserQueryRepository userQueryRepository;

    @Override
    public List<UserInfoResponseDto> findAllUserInfo() {
        List<User> all = userQueryRepository.findAll();
        List<UserInfoResponseDto> userInfoResponseDtos=new ArrayList<>();
        for (User user : all) {
            userInfoResponseDtos.add(getUserInfoResponseDto(user));
        }
        return userInfoResponseDtos;

    }

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
                .role(user.getRole());
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

    @Transactional
    @Override
    public void changePassword(ChangePasswordRequestDto changePasswordRequestDto) {
        /*User byStudentNum = userQueryRepository.findByStudentNum(changePasswordRequestDto.getStudentNum())
                .orElseThrow(NotFoundUserException::new);
        if(!byStudentNum.getPassword().equals(changePasswordRequestDto.getCurrentPassword())){
            throw new IncorrectPasswordException();
        }
        byStudentNum.changePassword(changePasswordRequestDto.getNewPassword());*/
    }

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
