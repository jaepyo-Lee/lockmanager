package com.ime.lockmanager.locker.domain;

import com.ime.lockmanager.locker.application.port.in.req.LockerSetTimeRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
public class Period {
    @Column(name = "START_DATETIME")
    private LocalDateTime startDateTime;

    @Column(name = "END_DATETIME")
    private LocalDateTime endDateTime;


    public void modifiedDateTime(LockerSetTimeRequestDto requestDto){
        this.startDateTime = requestDto.getStartDateTime();
        this.endDateTime = requestDto.getEndDateTime();
    }
}
