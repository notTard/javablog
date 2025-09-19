package com.firstapp.blog.appblog.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firstapp.blog.appblog.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByTitle(String title);
}
