package com.library.backend.services;

import com.library.backend.entities.User;

public interface UserService {

    User register(User user);
    User findUserById(Long id);

    User findByUsername(String username);

    Boolean existsByUsername(String username);

    User update(User user, Long id);

    void deleteUserById(Long id);
}
