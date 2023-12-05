package com.ime.lockmanager.user.adapter.out;

import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.user.application.port.out.UserToReservationQueryPort;
import com.ime.lockmanager.user.application.port.out.res.AllUserInfoForAdminResponseDto;
import com.ime.lockmanager.user.application.port.out.res.UserInfoResponseDto;
import com.ime.lockmanager.user.domain.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ime.lockmanager.locker.domain.locker.QLocker.locker;
import static com.ime.lockmanager.locker.domain.lockerdetail.QLockerDetail.lockerDetail;
import static com.ime.lockmanager.reservation.domain.QReservation.reservation;
import static com.ime.lockmanager.user.domain.QUser.user;


@Repository
@RequiredArgsConstructor
public class UserQuerydslRepositoryImpl implements UserToReservationQueryPort {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<User> findAllOrderByStudentNumAsc(Major userMajor, Pageable pageable) {
        List<User> userInfos = jpaQueryFactory.selectFrom(user)
                .orderBy(user.studentNum.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(user.majorDetail.major.eq(userMajor))
                .fetch();
        int total = jpaQueryFactory
                .selectFrom(user)
                .where(user.majorDetail.major.eq(userMajor))
                .fetch().size();
        return new PageImpl<>(userInfos, pageable, total);
    }

    @Override
    public UserInfoResponseDto findUserInfoWithLockerIdByStudentNum(String studentNum) {
        BooleanBuilder builder = new BooleanBuilder();

        if (studentNum != null) {
            builder.and(user.studentNum.eq(studentNum));
        }

        UserInfoResponseDto userInfoResponseDto = jpaQueryFactory
                .select(Projections
                        .constructor(
                                UserInfoResponseDto.class,
                                user.name, user.membership, user.status,
                                user.studentNum, lockerDetail.lockerNum, locker.name, user.majorDetail.name
                        )
                )
                .from(user)
                .leftJoin(user.reservation, reservation)
                .leftJoin(reservation.lockerDetail, lockerDetail)
                .leftJoin(lockerDetail.locker, locker)
                .where(builder)
                .fetchOne();
        System.out.println(userInfoResponseDto.toString());
        return userInfoResponseDto;
    }
}
