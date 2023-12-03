package com.ime.lockmanager.user.application.service;

import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.reservation.application.port.in.ReservationUseCase;
import com.ime.lockmanager.reservation.application.service.RedissonLockReservationFacade;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.application.port.in.dto.UpdateUserDueInfoDto;
import com.ime.lockmanager.user.application.port.in.req.ModifiedUserInfoDto;
import com.ime.lockmanager.user.application.port.in.req.ModifiedUserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserCancelLockerRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.application.port.out.UserToReservationQueryPort;
import com.ime.lockmanager.user.application.port.out.res.AllUserInfoForAdminResponseDto;
import com.ime.lockmanager.user.application.port.out.res.UserInfoResponseDto;
import com.ime.lockmanager.user.application.service.dto.UserModifiedInfoDto;
import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
class UserService implements UserUseCase {
    private final UserQueryPort userQueryPort;
    private final ReservationUseCase reservationUseCase;
    private final UserToReservationQueryPort userToReservationQueryPort;
    private final RedissonLockReservationFacade redissonLockReservationFacade;
    private final LockerQueryPort lockerQueryPort;

    @Override
    public void modifiedUserInfo(ModifiedUserInfoRequestDto requestDto) throws Exception {
        for (ModifiedUserInfoDto modifiedUserInfo : requestDto.getModifiedUserInfoList()) {
            User requestUser = userQueryPort.findByStudentNum(modifiedUserInfo.getStudentNum())
                    .orElseThrow(NotFoundUserException::new);
            if (modifiedUserInfo.getLockerDetailId()==null) { // 요청된 사물함 정보가 없을때
                requestUser.modifiedUserInfo(UserModifiedInfoDto.fromModifiedUserInfoDto(modifiedUserInfo));
            } else {
                if (reservationUseCase.isReservationExistByStudentNum(modifiedUserInfo.getStudentNum())) { //예약이 되어있다면, 해당 사물함 취소후 재등록
                    reservationUseCase.cancelLockerByStudentNum(UserCancelLockerRequestDto.builder()
                            .studentNum(modifiedUserInfo.getStudentNum())
                            .build());
                }
                redissonLockReservationFacade.registerForAdmin(LockerRegisterRequestDto.builder() //일반예약은 lockerdetail의 PK값을 받아서 예약하는것이지만, 지금은 lockerdetail의 칸번호를 받고있으니 수정해야함
                        .studentNum(modifiedUserInfo.getStudentNum())
                        .lockerDetailId(modifiedUserInfo.getLockerDetailId())
                        .build());
                requestUser.modifiedUserInfo(UserModifiedInfoDto.fromModifiedUserInfoDto(modifiedUserInfo));
            }
        }
    }

    @Override
    public void updateUserDueInfoOrSave(UpdateUserDueInfoDto updateUserDueInfoDto) throws Exception {
        User byStudentNum = userQueryPort.findByStudentNum(updateUserDueInfoDto.getStudentNum())
                .orElseGet(() -> userQueryPort.save(User.builder()
                        .name(updateUserDueInfoDto.getName())
                        .studentNum(updateUserDueInfoDto.getStudentNum())
                        .membership(updateUserDueInfoDto.isDue())
                        .role(Role.ROLE_USER)
                        .auth(false)
                        .build()));
        if (byStudentNum.isAuth()) {
            if (updateUserDueInfoDto.isDue() != byStudentNum.isMembership()) {
                byStudentNum.updateDueInfo(updateUserDueInfoDto.isDue());
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AllUserInfoForAdminResponseDto> findAllUserInfo(String adminUserStudentNum, Pageable pageable) {
        User adminUser = userQueryPort.findByStudentNum(adminUserStudentNum).orElseThrow(NotFoundUserException::new);
        Major adminMajor = adminUser.getMajorDetail().getMajor();
        Page<AllUserInfoForAdminResponseDto> userPage = userToReservationQueryPort.findAllOrderByStudentNumAsc(
                adminMajor, pageable);

        return userPage;

    }

    @Transactional(readOnly = true)
    @Override
    public UserInfoResponseDto findUserInfoByStudentNum(UserInfoRequestDto userRequestDto) {
        UserInfoResponseDto userInfoWithLockerIdByStudentNum = userToReservationQueryPort
                .findUserInfoWithLockerIdByStudentNum(userRequestDto.getStudentNum());
        return userInfoWithLockerIdByStudentNum;
    }


    @Transactional(readOnly = true)
    @Override
    public boolean checkAdmin(String studentNum) {
        User byStudentNum = userQueryPort.findByStudentNum(studentNum)
                .orElseThrow(NotFoundUserException::new);
        if (byStudentNum.getRole() == Role.ROLE_ADMIN) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User findByStudentNum(String studentNum) {
        return userQueryPort.findByStudentNum(studentNum).orElseThrow(NotFoundUserException::new);
    }
}
