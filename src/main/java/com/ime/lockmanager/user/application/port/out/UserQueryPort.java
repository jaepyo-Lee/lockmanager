package com.ime.lockmanager.user.application.port.out;

import com.ime.lockmanager.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface UserQueryPort {
    Optional<User> findByStudentNum(String studentNum);



    List<User> findAll();

    void deleteAll();

    User save(User user);

}
