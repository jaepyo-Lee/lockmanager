package com.ime.lockmanager.major.adapter.out.major;

import com.ime.lockmanager.major.domain.Major;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.Optional;
import java.util.function.Function;

public interface MajorRepository extends JpaRepository<Major,Long> {
    @Override
    Optional<Major> findById(Long aLong);

    Optional<Major> findByName(String name);
}
