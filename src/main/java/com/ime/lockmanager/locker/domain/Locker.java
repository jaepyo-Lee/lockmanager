package com.ime.lockmanager.locker.domain;

import com.ime.lockmanager.common.domain.BaseTimeEntity;
import com.ime.lockmanager.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "LOCKER_TABLE")
public class Locker extends BaseTimeEntity {
    @Id
    @Column(name = "LOCKER_ID")
    private Long id;

    @OneToOne(mappedBy = "locker")
    private User user;

    private boolean usable;

}
