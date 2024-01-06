package com.ime.lockmanager.user.adapter.out;

import com.ime.lockmanager.auth.application.port.out.AuthToUserQueryPort;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserQueryRepository implements UserQueryPort, AuthToUserQueryPort {
    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByIdWithMajorDetailWithMajor(Long userId) {
        return userJpaRepository.findByIdWithMajorDetailWithMajor(userId);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId);
    }

    @Override
    public Optional<User> findByStudentNumWithMajorDetailWithMajor(String studentNum) {
        return userJpaRepository.findByStudentNumWithMajorDetailWithMajor(studentNum);
    }

    @Override
    public void deleteAll() {
        userJpaRepository.deleteAll();
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll();
    }


    @Override
    public Optional<User> findByStudentNameAndStudentNum(String studentName, String studentNum) {
        return userJpaRepository.findByNameAndStudentNum(studentName, studentNum);
    }

    @Override
    public Optional<User> findByStudentNum(String studentNum) {
        return userJpaRepository.findByStudentNum(studentNum);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }
}
