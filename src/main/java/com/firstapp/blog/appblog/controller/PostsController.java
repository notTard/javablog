package com.firstapp.blog.appblog.controller;

import org.springframework.web.bind.annotation.RestController;

import com.firstapp.blog.appblog.model.Post;
import com.firstapp.blog.appblog.service.PostService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class PostsController {
    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postService.findAllPosts();
    }
    @PostMapping("/makepost")
    public Post postAPost(@RequestBody Post post) {
        //TODO: process POST request
        return  postService.createPost(post);
    }
    @GetMapping("/{title}")
    public List<Post> getPostByTitle(@PathVariable String title) {
        return postService.getPostByTitle(title);
    }
    
    
}
