package com.ime.lockmanager.user.application.service;

import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.reservation.application.port.in.ReservationUseCase;
import com.ime.lockmanager.reservation.application.port.out.ReservationQueryPort;
import com.ime.lockmanager.reservation.application.port.out.dto.FindReservationByStudentNumDto;
import com.ime.lockmanager.reservation.application.service.RedissonLockReservationFacade;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.application.port.in.dto.UpdateUserDueInfoDto;
import com.ime.lockmanager.user.application.port.in.req.ModifiedUserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserCancelLockerRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.application.port.out.UserToReservationQueryPort;
import com.ime.lockmanager.user.application.port.out.res.UserInfoForAdminModifiedPageResponseDto;
import com.ime.lockmanager.user.application.port.out.res.UserInfoForMyPageResponseDto;
import com.ime.lockmanager.user.application.service.dto.UserModifiedInfoDto;
import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
class UserService implements UserUseCase {
    private final UserQueryPort userQueryPort;
    private final ReservationUseCase reservationUseCase;
    private final ReservationQueryPort reservationQueryPort;
    private final UserToReservationQueryPort userToReservationQueryPort;
    private final RedissonLockReservationFacade redissonLockReservationFacade;
    @Override
    public void modifiedUserInfo(List<ModifiedUserInfoRequestDto> requestDto) throws Exception {
        for (ModifiedUserInfoRequestDto modifiedUserInfoRequestDto : requestDto) {
            User byStudentNum = userQueryPort.findByStudentNum(modifiedUserInfoRequestDto.getStudentNum())
                    .orElseThrow(NotFoundUserException::new);
            if(modifiedUserInfoRequestDto.getLockerNumber()==""){
                byStudentNum.modifiedUserInfo(UserModifiedInfoDto.fromModifiedUserInfoRequestDto(modifiedUserInfoRequestDto));
            }else{
                if (reservationQueryPort.isReservationByStudentNum(FindReservationByStudentNumDto.builder()
                        .studentNum(modifiedUserInfoRequestDto.getStudentNum())
                        .build())) { //예약이 되어있다면, 해당 사물함 취소후 재등록
                    reservationUseCase.cancelLockerByStudentNum(UserCancelLockerRequestDto.builder()
                            .studentNum(modifiedUserInfoRequestDto.getStudentNum())
                            .build());
                }
                redissonLockReservationFacade.registerForAdmin(LockerRegisterRequestDto.builder()
                        .studentNum(modifiedUserInfoRequestDto.getStudentNum())
                        .lockerDetailId(Long.parseLong(modifiedUserInfoRequestDto.getLockerNumber()))
                        .build());
                byStudentNum.modifiedUserInfo(UserModifiedInfoDto.fromModifiedUserInfoRequestDto(modifiedUserInfoRequestDto));
            }
        }
    }

    @Override
    public void updateUserDueInfoOrSave(UpdateUserDueInfoDto updateUserDueInfoDto) throws Exception{
        User byStudentNum = userQueryPort.findByStudentNum(updateUserDueInfoDto.getStudentNum())
                .orElseGet(() -> userQueryPort.save(User.builder()
                        .name(updateUserDueInfoDto.getName())
                        .studentNum(updateUserDueInfoDto.getStudentNum())
                        .membership(updateUserDueInfoDto.isDue())
                        .role(Role.ROLE_USER)
                        .auth(false)
                        .build()));
        if(byStudentNum.isAuth()){
            if(updateUserDueInfoDto.isDue()!=byStudentNum.isMembership()){
                byStudentNum.updateDueInfo(updateUserDueInfoDto.isDue());
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserInfoForAdminModifiedPageResponseDto> findAllUserInfo(Pageable pageable) {
        Page<UserInfoForAdminModifiedPageResponseDto> userPage = userToReservationQueryPort.findAllOrderByStudentNumAsc(pageable);

        return userPage;

    }

    @Transactional(readOnly = true)
    @Override
    public UserInfoResponseDto findUserInfoByStudentNum(UserInfoRequestDto userRequestDto){
        UserInfoForMyPageResponseDto userInfoWithLockerIdByStudentNum = userToReservationQueryPort
                .findUserInfoWithLockerIdByStudentNum(userRequestDto.getStudentNum());
        return userInfoWithLockerIdByStudentNum.to();
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

    @Override
    public User findByStudentNum(String studentNum) {
        return userQueryPort.findByStudentNum(studentNum).orElseThrow(NotFoundUserException::new);
    }
}
