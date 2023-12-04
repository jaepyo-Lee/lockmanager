package com.ime.lockmanager.user.domain;

import com.ime.lockmanager.common.domain.BaseTimeEntity;
import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.major.domain.MajorDetail;
import com.ime.lockmanager.reservation.domain.Reservation;
import com.ime.lockmanager.user.application.service.dto.UserModifiedInfoDto;
import com.ime.lockmanager.user.domain.dto.UpdateUserInfoDto;
import com.ime.lockmanager.user.domain.response.UpdateUserStatusInfoDto;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

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
    @OneToOne(mappedBy = "user",fetch = LAZY)
    private Reservation reservation;
    private String grade;
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "major_detail_id")
    private MajorDetail majorDetail;
    private boolean auth;

    public void setNullReservation(){
        this.reservation = null;
    }

    @Builder
    public User(String name, String studentNum, String status, Role role) {
        this.name = name;
        this.studentNum = studentNum;
        this.status = status;
        this.role = role;
    }

    public void updateUserInfo(UpdateUserInfoDto updateUserInfoDto){
        this.auth = updateUserInfoDto.isAuth();
        this.status = updateUserInfoDto.getStatus();
        this.grade = updateUserInfoDto.getGrade();
        this.majorDetail = updateUserInfoDto.getMajorDetail();
    }

    public void modifiedUserInfo(UserModifiedInfoDto dto){
        this.role = dto.getRole();
        this.membership = dto.isMembership();
    }
    public void updateDueInfo(boolean isDue){
        this.membership = isDue;
    }
}
