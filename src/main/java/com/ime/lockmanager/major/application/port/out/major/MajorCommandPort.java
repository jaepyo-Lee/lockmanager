package com.ime.lockmanager.major.application.port.out.major;

import com.ime.lockmanager.major.domain.Major;
import org.springframework.stereotype.Repository;

public interface MajorCommandPort {
    Major save(Major major);

    void deleteAll();
}
