package com.ime.lockmanager.user.application.service;

import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.reservation.application.port.in.ReservationUseCase;
import com.ime.lockmanager.reservation.application.service.RedissonLockReservationFacade;
import com.ime.lockmanager.reservation.domain.Reservation;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.application.port.in.dto.UpdateUserDueInfoDto;
import com.ime.lockmanager.user.application.port.in.req.*;
import com.ime.lockmanager.user.application.port.in.res.AllApplyingStudentPageResponseDto;
import com.ime.lockmanager.user.application.port.in.res.AllApplyingStudentDto;
import com.ime.lockmanager.user.application.port.in.res.CheckMembershipResponseDto;
import com.ime.lockmanager.user.application.port.out.UserMembershipQueryPort;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.application.port.out.UserToReservationQueryPort;
import com.ime.lockmanager.user.application.port.out.res.AllUserInfoForAdminResponseDto;
import com.ime.lockmanager.user.application.port.out.res.UserInfoQueryResponseDto;
import com.ime.lockmanager.user.application.service.dto.UserModifiedInfoDto;
import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ime.lockmanager.reservation.domain.ReservationStatus.RESERVED;
import static com.ime.lockmanager.user.domain.MembershipState.APPROVE;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
class UserService implements UserUseCase {
    private final UserQueryPort userQueryPort;
    private final ReservationUseCase reservationUseCase;
    private final UserToReservationQueryPort userToReservationQueryPort;
    private final UserMembershipQueryPort userMembershipQueryPort;
    private final RedissonLockReservationFacade redissonLockReservationFacade;
    private final int PAGE_SIZE = 30;

    @Override
    public CheckMembershipResponseDto checkMembership(String studentNum) {
        User student = userQueryPort.findByStudentNum(studentNum)
                .orElseThrow(NotFoundUserException::new);
        return CheckMembershipResponseDto.builder()
                .membershipState(student.getMembershipState())
                .build();
    }

    @Override
    public String determineApplying(DetermineApplyingRequestDto requestDto, boolean isApprove) {
        User student = userQueryPort.findByStudentNum(requestDto.getStudentNum())
                .orElseThrow(NotFoundUserException::new);
        String msg;
        if (isApprove) {
            msg = student.approve();
        } else {
            msg = student.deny();
        }
        return msg;
    }

    @Override
    public String applyMembership(String studentNum) {
        User student = userQueryPort.findByStudentNum(studentNum)
                .orElseThrow(NotFoundUserException::new);
        if (student.getMembershipState().equals(APPROVE)) {
            throw new IllegalStateException("이미 승인된 사용자 입니다.");
        }
        return student.applyMembership();
    }

    @Override
    public AllApplyingStudentPageResponseDto findAllApplying(String studentNum, int page) {
        User user = userQueryPort.findByStudentNumWithMajorDetailWithMajor(studentNum)
                .orElseThrow(NotFoundUserException::new);
        Major major = user.getMajorDetail().getMajor();
        Page<User> membershipApplicants = userMembershipQueryPort
                .findAllMembershipApplicantOrderByStudentNumAsc(major, PageRequest.of(page, PAGE_SIZE));
        List<AllApplyingStudentDto> applicantInfos = membershipApplicants.stream()
                .map(applicant -> AllApplyingStudentDto.builder()
                        .studentName(applicant.getName())
                        .studentNum(applicant.getStudentNum())
                        .build())
                .collect(Collectors.toList());
        return AllApplyingStudentPageResponseDto.builder()
                .currentPage(membershipApplicants.getNumber())
                .totalPage(membershipApplicants.getTotalPages())
                .applicant(applicantInfos).build();
    }


    @Override
    public Optional<User> findByStudentNumWithMajorDetailWithMajor(String studentNum) {
        return userQueryPort.findByStudentNumWithMajorDetailWithMajor(studentNum);
    }

    @Override
    public void modifiedUserInfo(ModifiedUserInfoRequestDto requestDto) throws Exception {
        for (ModifiedUserInfoDto modifiedUserInfo : requestDto.getModifiedUserInfoList()) {
            User requestUser = userQueryPort.findByStudentNum(modifiedUserInfo.getStudentNum())
                    .orElseThrow(NotFoundUserException::new);
            if (modifiedUserInfo.getLockerDetailId() == null) { // 요청된 사물함 정보가 없을때
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
    public Page<AllUserInfoForAdminResponseDto> findAllUserInfo(String adminUserStudentNum, int page) {
        User adminUser = userQueryPort.findByStudentNum(adminUserStudentNum).orElseThrow(NotFoundUserException::new);
        Major adminMajor = adminUser.getMajorDetail().getMajor();

        Page<User> allUser = userToReservationQueryPort.findAllOrderByStudentNumAsc(adminMajor, PageRequest.of(page, PAGE_SIZE));
        PageRequest pageRequest = PageRequest.of(allUser.getNumber(), allUser.getSize());
        List<AllUserInfoForAdminResponseDto> userPageList = new ArrayList<>();
        for (User user : allUser) {
            AllUserInfoForAdminResponseDto build = AllUserInfoForAdminResponseDto.builder()
                    .studentNum(user.getStudentNum())
                    .status(user.getStatus())
                    .role(user.getRole())
                    .membershipState(user.getMembershipState())
                    .name(user.getName())
                    .lockerName(user.getReservation().stream()
                            .filter(reservation -> reservation.getReservationStatus().equals(RESERVED))
                            .findFirst()
                            .map(reservation -> reservation.getLockerDetail().getLocker().getName())
                            .orElse(null)
                    )
                    .lockerNum(
                            user.getReservation().stream()
                                    .filter(reservation -> reservation.getReservationStatus().equals(RESERVED))
                                    .findFirst()
                                    .map(reservation -> reservation.getLockerDetail().getLockerNum())
                                    .orElse(null)
                    ).build();
            userPageList.add(build);
        }
        PageImpl<AllUserInfoForAdminResponseDto> userPage = new PageImpl<>(userPageList, pageRequest, userPageList.size());


        return userPage;

    }

    @Transactional(readOnly = true)
    @Override
    public UserInfoQueryResponseDto findUserInfoByStudentNum(UserInfoRequestDto userRequestDto) {
        User user = userQueryPort.findByStudentNumWithMajorDetailWithMajor(userRequestDto.getStudentNum())
                .orElseThrow(NotFoundUserException::new);

        Reservation findReservation = user.getReservation().stream()
                .filter(reservation -> reservation.getReservationStatus().equals(RESERVED))
                .findFirst().orElse(null);

        UserInfoQueryResponseDto.UserInfoQueryResponseDtoBuilder userInfoQueryResponseDtoBuilder = UserInfoQueryResponseDto.builder()
                .majorDetail(user.getMajorDetail().getName())
                .studentNum(user.getStudentNum())
                .status(user.getStatus())
                .membershipState(user.getMembershipState())
                .name(user.getName());

        setReservationDetails(userInfoQueryResponseDtoBuilder, findReservation);

        return userInfoQueryResponseDtoBuilder.build();
    }

    private void setReservationDetails(UserInfoQueryResponseDto.UserInfoQueryResponseDtoBuilder builder, Reservation reservation) {
        if (reservation != null) {
            builder.lockerName(reservation.getLockerDetail().getLocker().getName())
                    .lockerNum(reservation.getLockerDetail().getLockerNum());
        } else {
            builder.lockerNum(null).lockerName(null);
        }
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
