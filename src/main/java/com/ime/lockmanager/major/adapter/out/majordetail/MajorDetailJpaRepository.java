package com.ime.lockmanager.major.adapter.out.majordetail;

import com.ime.lockmanager.major.domain.MajorDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface MajorDetailJpaRepository extends JpaRepository<MajorDetail,Long> {
    @Query("select MD from MAJOR_DETAIL_TABLE as MD join fetch MD.major where MD.name=:name")
    Optional<MajorDetail> findByNameWithMajor(@Param("name") String name);

    List<MajorDetail> findByMajorId(Long majorId);
}
