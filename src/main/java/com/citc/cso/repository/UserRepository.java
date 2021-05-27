package com.citc.cso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citc.cso.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
