package com.ime.lockmanager.user.application.port.out;

import com.ime.lockmanager.user.domain.User;

import java.util.Optional;

public interface UserQueryPort {
    Optional<User> findByStudentNum(String studentNum);
}
