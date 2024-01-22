package com.ime.lockmanager.reservation.domain;

import com.ime.lockmanager.common.domain.BaseTimeEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "RESERVATION_RECORD_TABLE")
public class ReservationRecord extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String lockerName;
    private String lockerNum;
    private String userName;
    private ReservationStatus reservationStatus;
}
