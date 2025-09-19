package com.firstapp.blog.appblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firstapp.blog.appblog.model.Post;
import com.firstapp.blog.appblog.repos.PostRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> findAllPosts(){
        return postRepository.findAll();
    }

    public void deletePost(Long id){
        postRepository.deleteById(id);
    }

    public Post createPost(Post post){
        return postRepository.save(post);
    }

    public List<Post> getPostByTitle(String title) {
        return postRepository.findByTitle(title);
    }

    public Post updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        
        return postRepository.save(post);
    }
}
