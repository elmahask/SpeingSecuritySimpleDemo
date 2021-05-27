package com.citc.cso.service;

import com.citc.cso.model.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
}

