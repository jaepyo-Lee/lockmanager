package com.ime.lockmanager.user.adapter.out;

import com.ime.lockmanager.user.application.port.out.res.UserInfoQueryResponseDto;
import com.ime.lockmanager.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User,Long>{
    Optional<User> findByStudentNum(String studentNum);

    Optional<User> findByNameAndStudentNum(String name,String studentNum);

    User save(User user);

    @Override
    List<User> findAll();

    @Override
    Page<User> findAll(Pageable pageable);

    @Query("select u.studentNum,u.name,u.membership from USER_TABLE u left join u.reservation ORDER BY u.studentNum ASC")
    Page<UserInfoQueryResponseDto> findAllOrderByStudentNumAsc(Pageable pageable);

    /*@Query("select " +
            "new com.ime.lockmanager.user.application.port.out.res.UserInfoWithLockerIdResponseDto(U.studentNum,U.name,U.membership,R.locker.id) " +
            "from  USER_TABLE as U join U.reservation AS R ON U.Id=R.user.Id where U.studentNum=:studentNum")
    List<UserInfoWithLockerIdResponseDto> findUserInfoWithReservationByStudentNum(@Param("studentNum") String studentNum);*/

    @Query("select U from USER_TABLE as U join fetch U.majorDetail as MD join fetch MD.major where U.studentNum = :studentNum")
    Optional<User> findByStudentNumWithMajorDetailWithMajor(@Param("studentNum") String studentNum);

    @Query("select U from USER_TABLE as U join fetch U.majorDetail as MD join fetch MD.major where U.id = :userId")
    Optional<User> findByIdWithMajorDetailWithMajor(@Param("userId") Long userId);
}
