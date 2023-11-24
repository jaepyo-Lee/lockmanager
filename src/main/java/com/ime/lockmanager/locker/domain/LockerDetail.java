package com.ime.lockmanager.locker.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "LOCKER_DETAIL_TABLE")
public class LockerDetail {
    @Id
    @GeneratedValue
    private Long id;

    private String row_num;
    private String column_num;
    private String locker_num;
    private boolean isUsable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locker_id")
    private Locker locker;
}
