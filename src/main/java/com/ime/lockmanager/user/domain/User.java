package com.ime.lockmanager.user.domain;

import com.ime.lockmanager.common.domain.BaseTimeEntity;
import com.ime.lockmanager.locker.domain.Locker;
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

    @Column(name = "MEMBERSHIP")
    private boolean membership;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    public void registerLocker(Locker locker){
        this.locker = locker;
        locker.setUsable(false);
        locker.setUser(this);
    }
}
