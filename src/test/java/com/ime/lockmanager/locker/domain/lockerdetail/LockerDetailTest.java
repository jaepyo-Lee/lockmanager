package com.ime.lockmanager.locker.domain.lockerdetail;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class LockerDetailTest {
    @Test
    void cancelTest(){
        //given
        LockerDetail lockerDetail = LockerDetail.builder()
                .id(1L)
                .lockerDetailStatus(LockerDetailStatus.RESERVED)
                .build();
        //when
        lockerDetail.cancel();
        //then
        Assertions.assertThat(lockerDetail.getLockerDetailStatus()).isEqualTo(LockerDetailStatus.NON_RESERVED);
    }
    @Test
    void reserveTrueTest(){
        //given
        LockerDetail lockerDetail = LockerDetail.builder()
                .id(1L)
                .lockerDetailStatus(LockerDetailStatus.NON_RESERVED)
                .build();
        //when
        lockerDetail.reserve();
        //then
        Assertions.assertThat(lockerDetail.getLockerDetailStatus()).isEqualTo(LockerDetailStatus.RESERVED);
    }
}