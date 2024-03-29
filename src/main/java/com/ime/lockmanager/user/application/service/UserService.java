package com.ime.lockmanager.user.application.service;

import com.ime.lockmanager.common.format.exception.major.majordetail.NotFoundMajorDetailException;
import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
import com.ime.lockmanager.major.application.port.out.major.MajorQueryPort;
import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.reservation.application.port.in.ReservationUseCase;
import com.ime.lockmanager.reservation.application.port.out.ReservationQueryPort;
import com.ime.lockmanager.reservation.domain.Reservation;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.application.port.in.dto.UpdateUserDueInfoDto;
import com.ime.lockmanager.user.application.port.in.req.*;
import com.ime.lockmanager.user.application.port.in.res.AllApplyingStudentPageResponseDto;
import com.ime.lockmanager.user.application.port.in.res.AllApplyingStudentDto;
import com.ime.lockmanager.user.application.port.in.res.CheckMembershipResponseDto;
import com.ime.lockmanager.user.application.port.in.res.UserTierResponseDto;
import com.ime.lockmanager.user.application.port.out.UserCommandPort;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.application.port.out.res.AllUserInfoForAdminResponseDto;
import com.ime.lockmanager.user.application.port.out.res.UserInfoQueryResponseDto;
import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.User;
import com.ime.lockmanager.user.domain.UserTier;
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

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
class UserService implements UserUseCase {
    private final UserQueryPort userQueryPort;
    private final UserCommandPort userCommandPort;
    private final MajorQueryPort majorQueryPort;
    private final ReservationQueryPort reservationQueryPort;
    private final ReservationUseCase reservationUseCase;
    private final int PAGE_SIZE = 30;

    @Override
    public UserTierResponseDto determineApplying(DetermineApplyingRequestDto requestDto, boolean isApprove) {
        User student = userQueryPort.findByStudentNum(requestDto.getStudentNum())
                .orElseThrow(NotFoundUserException::new);
        if (isApprove) {
            return UserTierResponseDto.builder()
                    .userTier(student.approve())
                    .build();
        }
        return UserTierResponseDto.builder()
                .userTier(student.deny())
                .build();
    }

    @Override
    public void applyMembership(Long userId) {
        User student = userQueryPort.findById(userId)
                .orElseThrow(NotFoundUserException::new);
        if (!student.getUserTier().equals(UserTier.NON_MEMBER)) {
            throw new IllegalStateException("이미 신청 또느 승인된 학우입니다.");
        }
        student.applyMembership();
    }

    @Override
    public AllApplyingStudentPageResponseDto findAllApplying(String studentNum, int page) {
        User user = userQueryPort.findByStudentNumWithMajorDetailAndMajor(studentNum)
                .orElseThrow(NotFoundUserException::new);
        Major major = user.getMajorDetail().getMajor();
        Page<User> membershipApplicants = userQueryPort
                .findApplicantsByMajorOrderByStudentNumAsc(major, PageRequest.of(page, PAGE_SIZE));
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

    /*
    관리자용 취소후 예약 로직이기 때문에 로직 수정 필요
     */
    @Override
    public void modifiedUserInfo(ModifiedUserInfoRequestDto requestDto) throws Exception {
        for (ModifiedUserInfoDto modifiedUserInfo : requestDto.getModifiedUserInfoList()) {
            User user = userQueryPort.findByStudentNum(modifiedUserInfo.getStudentNum())
                    .orElseThrow(NotFoundUserException::new);
            if (modifiedUserInfo.getLockerDetailId() != null) {
                reservationUseCase.reserveForAdmin(
                        LockerRegisterRequestDto.builder() //일반예약은 lockerdetail의 PK값을 받아서 예약하는것이지만, 지금은 lockerdetail의 칸번호를 받고있으니 수정해야함
                                .userId(user.getId())
                                .lockerDetailId(modifiedUserInfo.getLockerDetailId())
                                .build()
                );
            }
            if (modifiedUserInfo.getAdmin() != null) {
                user.changeAdmin(modifiedUserInfo.getAdmin().booleanValue());
            }
            if (modifiedUserInfo.getMembership() != null) {
                if (modifiedUserInfo.getMembership().booleanValue() == Boolean.TRUE) {//납부자로 변경하고싶을때
                    user.approve();
                } else {
                    user.deny();
                }
            }
        }
    }

    @Override
    public void updateUserDueInfoOrSave(List<UpdateUserDueInfoDto> updateUserDueInfoDto) throws Exception {
        List<User> newUsers = updateUserDueInfoDto.parallelStream()
                .filter(dto -> userQueryPort.findByStudentNum(dto.getStudentNum()).isEmpty())
                .map(dto -> User.builder()
                        .name(dto.getName())
                        .studentNum(dto.getStudentNum())
                        .userTier(UserTier.judge(dto.isDue()))
                        .role(Role.ROLE_USER)
                        .majorDetail(dto.getMajorDetail())
                        .auth(false)
                        .build()).collect(Collectors.toList());
        userCommandPort.saveAll(newUsers);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AllUserInfoForAdminResponseDto> findAllUserInfo(FindAllUserRequestDto requestDto) {
        Major major = majorQueryPort.findById(requestDto.getMajorId())
                .orElseThrow(NotFoundMajorDetailException::new);//예외 따로 처리해야함
        Page<User> allUser = userQueryPort
                .findAllByMajorASC(major, requestDto.getSearch(), PageRequest.of(requestDto.getPage(), PAGE_SIZE));
        PageRequest pageRequest = PageRequest.of(allUser.getNumber(), allUser.getSize());
        List<AllUserInfoForAdminResponseDto> userPageList = new ArrayList<>();
        for (User user : allUser) {
            Optional<Reservation> reservation = reservationQueryPort.findByUserId(user.getId());
            AllUserInfoForAdminResponseDto userInfo = AllUserInfoForAdminResponseDto.of(user,reservation);
            userPageList.add(userInfo);
        }
        PageImpl<AllUserInfoForAdminResponseDto> userPage = new PageImpl<>(userPageList, pageRequest, userPageList.size());
        return userPage;
    }

    @Transactional(readOnly = true)
    @Override
    public UserInfoQueryResponseDto findUserInfoByStudentNum(UserInfoRequestDto userRequestDto) {
        User user = userQueryPort.findByIdWithMajorDetailAndMajor(userRequestDto.getUserId())
                .orElseThrow(NotFoundUserException::new);
//        findByStudentNumWithMajorDetailWithMajor(userRequestDto.getStudentNum())
        Optional<Reservation> reservation = reservationQueryPort.findByUserId(user.getId());
        UserInfoQueryResponseDto.UserInfoQueryResponseDtoBuilder userInfoQueryResponseDtoBuilder = UserInfoQueryResponseDto.builder()
                .majorDetail(user.getMajorDetail().getName())
                .studentNum(user.getStudentNum())
                .userTier(user.getUserTier())
                .userState(user.getUserState())
                .name(user.getName());

        setReservationDetails(userInfoQueryResponseDtoBuilder, reservation);

        return userInfoQueryResponseDtoBuilder.build();
    }

    private void setReservationDetails(UserInfoQueryResponseDto.UserInfoQueryResponseDtoBuilder builder,
                                       Optional<Reservation> findReservation) {
        if (findReservation.isPresent()) {
            Reservation reservation = findReservation.get();
            LockerDetail lockerDetail = reservation.getLockerDetail();
            builder.lockerName(lockerDetail.getLocker().getName())
                    .lockerDetailNum(lockerDetail.getLockerNum()).lockerDetailId(lockerDetail.getId());

        } else {
            builder
                    .lockerDetailNum(null)
                    .lockerName(null)
                    .lockerDetailId(null);
        }
    }
}
