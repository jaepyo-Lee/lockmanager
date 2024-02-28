package com.ime.lockmanager.major.application.port.out.majordetail;

import com.ime.lockmanager.major.domain.MajorDetail;

public interface MajorDetailCommandPort {
    MajorDetail save(MajorDetail majorDetail);
    void deleteAll();
}
