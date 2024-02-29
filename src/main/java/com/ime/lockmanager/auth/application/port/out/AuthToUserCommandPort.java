package com.ime.lockmanager.auth.application.port.out;

import com.ime.lockmanager.user.domain.User;

public interface AuthToUserCommandPort {
    User save(User user);
}
