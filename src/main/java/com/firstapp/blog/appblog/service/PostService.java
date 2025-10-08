package com.firstapp.blog.appblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.firstapp.blog.appblog.model.Post;
import com.firstapp.blog.appblog.model.User;
import com.firstapp.blog.appblog.repos.PostRepository;

@Service
@Transactional
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Transactional(readOnly = true)
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
    public void deleteByAuthor(Long postId, Long authorId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getAuthor().getId().equals(authorId)) {
            throw new RuntimeException("You are not allowed to delete this post");
        }
        postRepository.delete(post);
    }

    public List<Post> findPostsByUserId(Long userId) {
        // Сначала создаем объект User с нужным ID
        User author = new User();
        author.setId(userId);
        
        // Затем ищем посты этого автора
        return postRepository.findByAuthor(author);
    }
}
