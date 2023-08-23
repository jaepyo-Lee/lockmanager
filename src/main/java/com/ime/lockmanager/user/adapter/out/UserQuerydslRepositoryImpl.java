package com.ime.lockmanager.user.adapter.out;

import com.ime.lockmanager.user.application.port.out.UserToReservationQueryPort;
import com.ime.lockmanager.user.application.port.out.res.UserInfoForAdminModifiedPageResponseDto;
import com.ime.lockmanager.user.application.port.out.res.UserInfoForMyPageResponseDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ime.lockmanager.reservation.domain.QReservation.reservation;
import static com.ime.lockmanager.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserQuerydslRepositoryImpl implements UserToReservationQueryPort {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<UserInfoForAdminModifiedPageResponseDto> findAllOrderByStudentNumAsc(Pageable pageable) {
        List<UserInfoForAdminModifiedPageResponseDto> userInfos = jpaQueryFactory.select(Projections
                        .constructor(UserInfoForAdminModifiedPageResponseDto.class,
                                user.name, user.membership, user.status, user.studentNum, user.reservation.locker.id, user.role
                        )
                ).from(user)
                .leftJoin(user.reservation, reservation)
                .orderBy(user.studentNum.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        int total = jpaQueryFactory
                .selectFrom(user)
                .leftJoin(user.reservation, reservation)
                .fetch().size();
        return new PageImpl<>(userInfos, pageable, total);
    }

    @Override
    public UserInfoForMyPageResponseDto findUserInfoWithLockerIdByStudentNum(String studentNum) {
        BooleanBuilder builder = new BooleanBuilder();

        if (user.studentNum.equals(studentNum)) {
            builder.and(user.studentNum.eq(studentNum));
        }

        UserInfoForMyPageResponseDto userInfoForMyPageResponseDto = jpaQueryFactory
                .select(Projections
                        .constructor(
                                UserInfoForMyPageResponseDto.class,
                                user.name, user.membership, user.status, user.studentNum, user.reservation.locker.id
                        )
                )
                .from(user)
                .leftJoin(user.reservation, reservation)
                .where(builder)
                .fetchOne();
        System.out.println(userInfoForMyPageResponseDto.toString());
        return userInfoForMyPageResponseDto;
    }
}
