package com.ime.lockmanager.user.adapter.out;

import com.ime.lockmanager.auth.application.port.out.AuthToUserQueryPort;
import com.ime.lockmanager.major.domain.Major;
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
    private final UserQuerydslRepository userQuerydslRepository;

    @Override
    public Page<User> findApplicantsByMajorOrderByStudentNumAsc(Major major, Pageable pageable) {
        return userQuerydslRepository.findApplicantsByMajorOrderByStudentNumAsc(major, pageable);
    }

    @Override
    public Page<User> findAllByMajorASC(Major major, String search, Pageable pageable) {
        return userQuerydslRepository.findAllByMajorASC(major, search, pageable);
    }

    @Override
    public Optional<User> findByIdWithMajorDetailAndMajor(Long userId) {
        return userJpaRepository.findByIdWithMajorDetailAndMajor(userId);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId);
    }

    @Override
    public Optional<User> findByStudentNumWithMajorDetailAndMajor(String studentNum) {
        return userJpaRepository.findByStudentNumWithMajorDetailAndMajor(studentNum);
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


}
