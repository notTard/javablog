package com.firstapp.blog.appblog.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firstapp.blog.appblog.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    //User findById(Long id);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}
