package com.ime.lockmanager.user.application.port.out;

import com.ime.lockmanager.user.domain.User;

import java.util.List;

public interface UserCommandPort {
    void deleteAll();

    User save(User user);

    void saveAll(List<User> users);
}
