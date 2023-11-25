package com.ime.lockmanager.locker.domain;

import com.ime.lockmanager.common.domain.BaseTimeEntity;
import com.ime.lockmanager.locker.application.port.in.req.LockerSetTimeRequestDto;
import com.ime.lockmanager.locker.domain.dto.LockerCreateDto;
import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.reservation.domain.Reservation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;

import static java.time.LocalDateTime.now;
import static javax.persistence.FetchType.LAZY;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "LOCKER_TABLE")
public class Locker extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    @Schema(name = "사물함 예약 기간")
    @Embedded
    private Period period;

    @Schema(name = "사물함명")
    private String name;

    @Schema(name = "사물함 보유 학과")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "major_id")
    private Major major;

    //===================//
    @Schema(name = "사물함의 예약현황")
    @OneToOne(mappedBy = "locker",fetch = LAZY)
    private Reservation reservation;
    //===================//
    private String imageName;
    private String imageUrl;
    private String totalRow;
    private String totalColumn;

    public void modifiedDateTime(LockerSetTimeRequestDto requestDto){
        this.period = Period.builder()
                .startDateTime(requestDto.getStartDateTime())
                .endDateTime(requestDto.getEndDateTime())
                .build();
    }

    public static Locker createLocker(LockerCreateDto lockercreateDto){
        return Locker.builder()
                .name(lockercreateDto.getLockerName())
                .major(lockercreateDto.getMajor())
                .period(getPeriod(lockercreateDto))
                .build();
    }

    private static Period getPeriod(LockerCreateDto lockercreateDto) {
        return Period.builder()
                .startDateTime(lockercreateDto.getStartReservationTime())
                .endDateTime(lockercreateDto.getEndReservationTime())
                .build();
    }

    public boolean isDeadlineValid(){
        return this.period.getEndDateTime().isAfter(now()) &&
                this.period.getStartDateTime().isBefore(now());
    }
}
