package com.ime.lockmanager.user.domain;

import com.ime.lockmanager.common.domain.BaseTimeEntity;
import com.ime.lockmanager.locker.domain.Locker;
import com.ime.lockmanager.reservation.domain.Reservation;
import com.ime.lockmanager.user.application.service.dto.UserModifiedInfoDto;
import com.ime.lockmanager.user.domain.response.UpdateUserInfoDto;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Null;

@Getter
@Builder
@Entity(name = "USER_TABLE")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long Id;

    @Column(name = "NAME")
    private String name;
    @Column(name = "STUDENT_NUM")
    private String studentNum;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "MEMBERSHIP")
    private boolean membership;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user")
    private Reservation reservation;

    @Builder
    public User(String name, String studentNum, String status, Role role) {
        this.name = name;
        this.studentNum = studentNum;
        this.status = status;
        this.role = role;
    }

    public void updateUserInfo(UpdateUserInfoDto updateUserInfoDto){
        this.status = updateUserInfoDto.getStatus();
    }

    public void modifiedUserInfo(UserModifiedInfoDto dto){
        this.role = dto.getRole();
        this.membership = dto.isMembership();
    }
}
