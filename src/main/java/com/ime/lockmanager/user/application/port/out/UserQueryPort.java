package com.ime.lockmanager.user.application.port.out;

import com.ime.lockmanager.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserQueryPort {
    Optional<User> findByStudentNum(String studentNum);

    Page<User> findAllOrderByStudentNumAsc(Pageable pageable);

    List<User> findAll();
}
