package com.ime.lockmanager.major.adapter.out.major;

import com.ime.lockmanager.major.application.port.out.major.MajorQueryPort;
import com.ime.lockmanager.major.domain.Major;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MajorQueryRepository implements MajorQueryPort {
    private final MajorJpaRepository majorJpaRepository;

    @Override
    public List<Major> findAll() {
        return majorJpaRepository.findAll();
    }

    @Override
    public Optional<Major> findById(Long majorId) {
        return majorJpaRepository.findById(majorId);
    }

    @Override
    public Optional<Major> findByName(String name) {
        return majorJpaRepository.findByName(name);
    }
}
