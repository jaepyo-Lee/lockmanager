package com.ime.lockmanager.user.domain;

import com.ime.lockmanager.common.domain.BaseTimeEntity;
import com.ime.lockmanager.reservation.domain.Reservation;
import com.ime.lockmanager.user.application.service.dto.UserModifiedInfoDto;
import com.ime.lockmanager.user.domain.dto.UpdateUserInfoDto;
import com.ime.lockmanager.user.domain.response.UpdateUserStatusInfoDto;
import lombok.*;

import javax.persistence.*;

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
    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY)
    private Reservation reservation;

    private String grade;
    private String major;
    private boolean auth;


    @Builder
    public User(String name, String studentNum, String status, Role role) {
        this.name = name;
        this.studentNum = studentNum;
        this.status = status;
        this.role = role;
    }

    public void updateUserStatusInfo(UpdateUserStatusInfoDto updateUserStatusInfoDto){
        this.status = updateUserStatusInfoDto.getStatus();
    }

    public void updateUserInfo(UpdateUserInfoDto updateUserInfoDto){
        this.auth = updateUserInfoDto.isAuth();
        this.status = updateUserInfoDto.getStatus();
        this.grade = updateUserInfoDto.getGrade();
        this.major = updateUserInfoDto.getMajor();
    }

    public void modifiedUserInfo(UserModifiedInfoDto dto){
        this.role = dto.getRole();
        this.membership = dto.isMembership();
    }
    public void updateDueInfo(boolean isDue){
        this.membership = isDue;
    }
}
