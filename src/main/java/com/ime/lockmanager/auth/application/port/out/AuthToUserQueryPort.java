package com.ime.lockmanager.auth.application.port.out;

import com.ime.lockmanager.user.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;

public interface AuthToUserQueryPort {
    Optional<User> findByStudentNameAndStudentNum(String studentName,String studentNum);

    Optional<User>findByStudentNum(String studentNum);

    User save(User user);
}
