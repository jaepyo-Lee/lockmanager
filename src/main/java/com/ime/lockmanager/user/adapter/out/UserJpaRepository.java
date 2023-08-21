package com.ime.lockmanager.user.adapter.out;

import com.ime.lockmanager.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User,Long> {
    Optional<User> findByStudentNum(String studentNum);

    Optional<User> findByNameAndStudentNum(String name,String studentNum);

    User save(User user);

    @Override
    List<User> findAll();

    @Override
    Page<User> findAll(Pageable pageable);

    @Query("select u from USER_TABLE u ORDER BY u.studentNum ASC")
    Page<User> findAllOrderByStudentNumAsc(Pageable pageable);
}
