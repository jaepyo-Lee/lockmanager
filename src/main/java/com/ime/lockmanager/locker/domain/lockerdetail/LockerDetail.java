package com.ime.lockmanager.locker.domain.lockerdetail;

import com.ime.lockmanager.common.domain.BaseTimeEntity;
import com.ime.lockmanager.locker.domain.locker.Locker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity(name = "LOCKER_DETAIL_TABLE")
public class LockerDetail extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String row_num;
    private String column_num;
    private String lockerNum;
    @Enumerated(EnumType.STRING)
    private LockerDetailStatus lockerDetailStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locker_id")
    private Locker locker;

    public Long reserve(){
        this.lockerDetailStatus = LockerDetailStatus.RESERVED;
        return id;
    }
    public Long cancel(){
        this.lockerDetailStatus = LockerDetailStatus.NON_RESERVED;
        return this.id;
    }
}
