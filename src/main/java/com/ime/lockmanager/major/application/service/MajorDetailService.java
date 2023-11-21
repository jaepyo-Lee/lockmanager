package com.ime.lockmanager.major.application.service;

import com.ime.lockmanager.major.application.port.in.MajorDetailUseCase;
import com.ime.lockmanager.major.application.port.out.MajorDetailQueryPort;
import com.ime.lockmanager.major.domain.MajorDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MajorDetailService implements MajorDetailUseCase {
    private final MajorDetailQueryPort majorDetailQueryPort;

    @Override
    public Optional<MajorDetail> findMajorDetailByName(String majorDetailName) {
        return majorDetailQueryPort.findMajorDetailByName(majorDetailName);
    }
}
