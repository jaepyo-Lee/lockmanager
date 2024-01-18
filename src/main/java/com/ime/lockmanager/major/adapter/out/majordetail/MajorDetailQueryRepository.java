package com.ime.lockmanager.major.adapter.out.majordetail;

import com.ime.lockmanager.major.application.port.out.MajorDetailQueryPort;
import com.ime.lockmanager.major.domain.MajorDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MajorDetailQueryRepository implements MajorDetailQueryPort {
    private final MajorDetailRepository majorDetailRepository;

    @Override
    public void deleteAll() {
        majorDetailRepository.deleteAll();
    }

    @Override
    public Optional<MajorDetail> findByNameWithMajor(String majorName) {
        return majorDetailRepository.findByNameWithMajor(majorName);
    }

    @Override
    public MajorDetail save(MajorDetail majorDetail) {
        return majorDetailRepository.save(majorDetail);
    }

    @Override
    public List<MajorDetail> findAll() {
        return majorDetailRepository.findAll();
    }

    @Override
    public List<MajorDetail> findAllByMajorId(Long majorId) {
        return majorDetailRepository.findByMajorId(majorId);
    }
}