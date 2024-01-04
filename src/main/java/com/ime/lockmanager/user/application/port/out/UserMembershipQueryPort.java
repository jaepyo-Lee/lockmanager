package com.ime.lockmanager.user.application.port.out;

import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserMembershipQueryPort {
    Page<User> findAllMembershipApplicantOrderByStudentNumAsc(Major major, Pageable pageable);
}
