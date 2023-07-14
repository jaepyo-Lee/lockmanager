package com.ime.lockmanager.user.adapter.out;

import com.ime.lockmanager.auth.application.port.out.AuthToUserQueryPort;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserQueryRepository implements UserQueryPort, AuthToUserQueryPort {
    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByStudentNameAndStudentNum(String studentName, String studentNum) {
        return userJpaRepository.findByNameAndStudentNum(studentName, studentNum);
    }

    @Override
    public Optional<User> findByStudentNum(String studentNum) {
        return userJpaRepository.findByStudentNum(studentNum);
    }

}
