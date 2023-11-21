package com.ime.lockmanager.major.adapter.out;

import com.ime.lockmanager.major.application.port.out.MajorDetailQueryPort;
import com.ime.lockmanager.major.domain.MajorDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MajorDetailQueryRepository implements MajorDetailQueryPort {
    private final MajorDetailRepository majorDetailRepository;
    @Override
    public Optional<MajorDetail> findMajorDetailByName(String majorName) {
        return majorDetailRepository.findMajorDetailByName(majorName);
    }
}
