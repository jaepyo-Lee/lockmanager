package com.ime.lockmanager.locker.domain.locker;

import com.ime.lockmanager.common.domain.BaseTimeEntity;
import com.ime.lockmanager.locker.application.port.in.req.LockerSetTimeRequestDto;
import com.ime.lockmanager.locker.domain.ImageInfo;
import com.ime.lockmanager.locker.domain.Period;
import com.ime.lockmanager.locker.domain.locker.dto.LockerCreateDto;
import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.reservation.domain.Reservation;
import com.ime.lockmanager.user.domain.UserState;
import com.ime.lockmanager.user.domain.UserTier;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.time.LocalDateTime.now;
import static javax.persistence.FetchType.LAZY;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "LOCKER_TABLE")
public class Locker extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private String imageUrl;
    private String totalRow;
    private String totalColumn;

    @ElementCollection(targetClass = UserState.class)
    @JoinTable(name = "PERMIT_USER_STATE_TABLE", joinColumns = @JoinColumn(name = "locker_id"))
    @Column(name = "permitUserState")
    @Enumerated(EnumType.STRING)
    private Set<UserState> permitUserState = new HashSet<>();

    @ElementCollection(targetClass = UserTier.class)
    @JoinTable(name = "PERMIT_USER_Tier_TABLE", joinColumns = @JoinColumn(name = "locker_id"))
    @Column(name = "permitUserTier")
    @Enumerated(EnumType.STRING)
    private Set<UserTier> permitUserTier = new HashSet<>();

    public void modifiedDateTime(Period period) {
        this.period = period;
    }

    public void modifiedImageInfo(String newImageUrl) {
        this.imageUrl=newImageUrl;
    }


    public static Locker createLocker(LockerCreateDto lockercreateDto) {
        return Locker.builder()
                .period(new Period(lockercreateDto.getStartReservationTime(), lockercreateDto.getEndReservationTime()))
                .name(lockercreateDto.getLockerName())
                .major(lockercreateDto.getMajor())
                .totalColumn(lockercreateDto.getTotalColumn())
                .totalRow(lockercreateDto.getTotalRow())
                .imageUrl(lockercreateDto.getImageUrl())
                .permitUserTier(Set.copyOf(lockercreateDto.getUserTiers()))
                .permitUserState(Set.copyOf(lockercreateDto.getUserStates()))
                .build();
    }

    public boolean isDeadlineValid() {
        return this.period.getEndDateTime().isAfter(now()) &&
                this.period.getStartDateTime().isBefore(now());
    }
    public void rename(String rename){
        this.name = rename;
    }
}
