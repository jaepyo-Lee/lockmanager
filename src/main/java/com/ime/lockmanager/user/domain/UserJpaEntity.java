package com.ime.lockmanager.user.domain;

public class UserJpaEntity {
    public static User of(User user){
        return User.builder()
                .role(user.getRole())
                .Id(user.getId())
                .name(user.getName())
                .status(user.getStatus())
                .membership(user.isMembership())
                .studentNum(user.getStudentNum())
                .build();
    }
}
