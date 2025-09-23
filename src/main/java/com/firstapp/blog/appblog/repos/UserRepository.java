package com.firstapp.blog.appblog.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.firstapp.blog.appblog.model.User;

public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
