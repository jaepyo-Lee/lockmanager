package com.ime.lockmanager.major.adapter.out.major;

import com.ime.lockmanager.major.application.port.out.MajorQueryPort;
import com.ime.lockmanager.major.domain.Major;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MajorQueryRepository implements MajorQueryPort {
    private final MajorRepository majorRepository;
    @Override
    public Optional<Major> findById(Long majorId) {
        return majorRepository.findById(majorId);
    }

    @Override
    public Major save(Major major) {
        return majorRepository.save(major);
    }

    @Override
    public Optional<Major> findByName(String name) {
        return majorRepository.findByName(name);
    }
}
