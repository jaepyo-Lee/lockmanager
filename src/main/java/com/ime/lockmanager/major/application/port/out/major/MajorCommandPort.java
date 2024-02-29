package com.ime.lockmanager.major.application.port.out.major;

import com.ime.lockmanager.major.domain.Major;

public interface MajorCommandPort {
    Major save(Major major);

    void deleteAll();
}
