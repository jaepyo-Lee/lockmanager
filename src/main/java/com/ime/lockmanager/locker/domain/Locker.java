package com.ime.lockmanager.locker.domain;

import com.ime.lockmanager.common.domain.BaseTimeEntity;
import com.ime.lockmanager.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "LOCKER_TABLE")
public class Locker extends BaseTimeEntity {
    @Id
    @Column(name = "LOCKER_ID")
    private Long id;

    @OneToOne(mappedBy = "locker")
    private User user;

    private boolean usable;

    @Builder.Default
    @Column(name = "RESERVED_TIME")
    private LocalDateTime reservedTime=null;

}
