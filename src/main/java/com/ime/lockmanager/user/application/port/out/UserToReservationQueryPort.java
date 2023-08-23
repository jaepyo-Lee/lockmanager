package com.ime.lockmanager.user.application.port.out;

import com.ime.lockmanager.user.application.port.out.res.UserInfoForAdminModifiedPageResponseDto;
import com.ime.lockmanager.user.application.port.out.res.UserInfoForMyPageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserToReservationQueryPort {
    UserInfoForMyPageResponseDto findUserInfoWithLockerIdByStudentNum(String studentNum);
    Page<UserInfoForAdminModifiedPageResponseDto> findAllOrderByStudentNumAsc(Pageable pageable);
}
