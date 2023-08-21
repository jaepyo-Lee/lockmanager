package com.ime.lockmanager.user.application.service;

import com.ime.lockmanager.common.format.exception.locker.InvalidCancelLockerException;
import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.application.service.RedissonLockLockerFacade;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.application.port.in.req.ModifiedUserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserCancelLockerRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto.UserInfoResponseDtoBuilder;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.application.service.dto.UserModifiedInfoDto;
import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
class UserService implements UserUseCase {
    private final UserQueryPort userQueryPort;
    private final LockerQueryPort lockerQueryPort;
    private final RedissonLockLockerFacade lockLockerFacade;
    @Override
    public void modifiedUserInfo(List<ModifiedUserInfoRequestDto> requestDto) throws Exception {
        for (ModifiedUserInfoRequestDto modifiedUserInfoRequestDto : requestDto) {
            User byStudentNum = userQueryPort.findByStudentNum(modifiedUserInfoRequestDto.getStudentNum())
                    .orElseThrow(NotFoundUserException::new);
            if(modifiedUserInfoRequestDto.getLockerNumber()==""){
                byStudentNum.modifiedUserInfo(UserModifiedInfoDto.fromModifiedUserInfoRequestDto(modifiedUserInfoRequestDto));
            }else{
                if(byStudentNum.getLocker()!=null){
                    cancelLockerByStudentNum(UserCancelLockerRequestDto.builder()
                            .studentNum(modifiedUserInfoRequestDto.getStudentNum())
                            .build());
                }
                lockLockerFacade.register(LockerRegisterRequestDto.builder()
                        .studentNum(modifiedUserInfoRequestDto.getStudentNum())
                        .lockerNum(Long.parseLong(modifiedUserInfoRequestDto.getLockerNumber()))
                        .build());
                byStudentNum.modifiedUserInfo(UserModifiedInfoDto.fromModifiedUserInfoRequestDto(modifiedUserInfoRequestDto));
            }
        }
    }

    @Override
    public void cancelLockerByStudentNum(UserCancelLockerRequestDto cancelLockerDto) {
        User byStudentNum = userQueryPort.findByStudentNum(cancelLockerDto.getStudentNum())
                .orElseThrow(NotFoundUserException::new);
        if(byStudentNum.getLocker()==null){
            throw new InvalidCancelLockerException();
        }
        log.info("{} : 사물함 취소",cancelLockerDto.getStudentNum());
        byStudentNum.cancelLocker();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserInfoResponseDto> findAllUserInfo(Pageable pageable) {
        Page<User> userPage = userQueryPort.findAllOrderByStudentNumAsc(pageable);

        List<UserInfoResponseDto> userInfoResponseDtos=new ArrayList<>();

        return userPage.map(UserService::getUserInfoResponseDto);

    }

    @Transactional(readOnly = true)
    @Override
    public UserInfoResponseDto findUserInfoByStudentNum(UserInfoRequestDto userRequestDto){
        User byStudentNum = userQueryPort.findByStudentNum(userRequestDto.getStudentNum())
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
        User byStudentNum = userQueryPort.findByStudentNum(studentNum)
                .orElseThrow(NotFoundUserException::new);
        if(byStudentNum.getRole()== Role.ROLE_ADMIN){
            return true;
        }else{
            return false;
        }
    }
}
