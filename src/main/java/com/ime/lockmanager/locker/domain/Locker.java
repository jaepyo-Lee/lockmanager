package com.ime.lockmanager.locker.domain;

import com.ime.lockmanager.common.domain.BaseTimeEntity;
import com.ime.lockmanager.locker.application.port.in.req.LockerSetTimeRequestDto;
import com.ime.lockmanager.reservation.domain.Reservation;
import com.ime.lockmanager.user.domain.User;
import lombok.*;
import reactor.util.annotation.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "LOCKER_TABLE")
public class Locker extends BaseTimeEntity {
    @Id
    @Column(name = "LOCKER_ID")
    private Long id;
    @Embedded
    private Period period;

    @OneToOne(mappedBy = "locker",fetch = FetchType.LAZY)
    private Reservation reservation;

    public void modifiedDateTime(LockerSetTimeRequestDto requestDto){
        this.period = Period.builder()
                .startDateTime(requestDto.getStartDateTime())
                .endDateTime(requestDto.getEndDateTime())
                .build();
    }
}
