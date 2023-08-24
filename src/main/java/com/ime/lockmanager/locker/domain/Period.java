package com.ime.lockmanager.locker.domain;

import com.ime.lockmanager.locker.application.port.in.req.LockerSetTimeRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
public class Period {
    @Column(name = "START_DATETIME")
    private LocalDateTime startDateTime;

    @Column(name = "END_DATETIME")
    private LocalDateTime endDateTime;


}
