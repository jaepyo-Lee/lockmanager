package com.ime.lockmanager.major.adapter.out.major;

import com.ime.lockmanager.major.domain.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MajorRepository extends JpaRepository<Major,Long> {
    @Override
    Optional<Major> findById(Long aLong);
}
