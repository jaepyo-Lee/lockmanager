package com.ime.lockmanager.user.application.port.out;

import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.user.application.port.out.res.AllUserInfoForAdminResponseDto;
import com.ime.lockmanager.user.application.port.out.res.UserInfoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserToReservationQueryPort {
    UserInfoResponseDto findUserInfoWithLockerIdByStudentNum(String studentNum);
    Page<AllUserInfoForAdminResponseDto> findAllOrderByStudentNumAsc(Major major, Pageable pageable);
}
