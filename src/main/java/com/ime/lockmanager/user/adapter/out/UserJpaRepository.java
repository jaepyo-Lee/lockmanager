package com.ime.lockmanager.user.adapter.out;

import com.ime.lockmanager.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User,Long> {
    Optional<User> findByStudentNum(String studentNum);

    Optional<User> findByNameAndStudentNum(String name,String studentNum);

    User save(User user);
}
