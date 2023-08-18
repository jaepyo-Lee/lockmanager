package com.ime.lockmanager.user.application.port.out;

import com.ime.lockmanager.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
public interface UserQueryPort {
    Optional<User> findByStudentNum(String studentNum);

    Page<User> findAllOrderByStudentNumAsc(Pageable pageable);

    List<User> findAll();

    void deleteAll();

    User save(User user);
}
