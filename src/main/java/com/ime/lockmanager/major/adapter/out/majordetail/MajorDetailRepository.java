package com.ime.lockmanager.major.adapter.out.majordetail;

import com.ime.lockmanager.major.domain.MajorDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MajorDetailRepository extends JpaRepository<MajorDetail,Long> {
    Optional<MajorDetail> findMajorDetailByName(String name);
}
