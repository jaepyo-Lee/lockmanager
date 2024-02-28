package com.ime.lockmanager.user.adapter.out;

import com.ime.lockmanager.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByStudentNum(String studentNum);

    Optional<User> findByNameAndStudentNum(String name, String studentNum);

    User save(User user);

    @Override
    List<User> findAll();

    @Override
    Page<User> findAll(Pageable pageable);

    @Query("select U from USER_TABLE as U" +
            " join fetch U.majorDetail as MD" +
            " join fetch MD.major" +
            " where U.studentNum = :studentNum")
    Optional<User> findByStudentNumWithMajorDetailAndMajor(@Param("studentNum") String studentNum);

    @Query("select U from USER_TABLE as U join fetch U.majorDetail as MD join fetch MD.major where U.id = :userId")
    Optional<User> findByIdWithMajorDetailAndMajor(@Param("userId") Long userId);
}
