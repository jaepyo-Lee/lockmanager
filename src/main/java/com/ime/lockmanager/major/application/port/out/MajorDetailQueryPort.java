package com.ime.lockmanager.major.application.port.out;

import com.ime.lockmanager.major.domain.MajorDetail;

import java.util.Optional;

public interface MajorDetailQueryPort {
    Optional<MajorDetail> findMajorDetailByName(String majorName);
}
