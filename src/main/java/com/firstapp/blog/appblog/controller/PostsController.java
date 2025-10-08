package com.firstapp.blog.appblog.controller;

import org.springframework.web.bind.annotation.RestController;

import com.firstapp.blog.appblog.UserDetails.UserDetailsImpl;
import com.firstapp.blog.appblog.controller.DTO.MyPostsDTO;
import com.firstapp.blog.appblog.controller.DTO.PostResponse;
import com.firstapp.blog.appblog.model.Post;
import com.firstapp.blog.appblog.model.User;
import com.firstapp.blog.appblog.service.PostService;
import com.firstapp.blog.appblog.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class PostsController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    //private UserDetailsImpl userDetails;

    @GetMapping("/posts")
    public List<PostResponse> getAllPosts() {
        return postService.findAllPosts().stream()
            .map(PostResponse::fromEntity)
            .collect(Collectors.toList());
    }


    @PostMapping("/makepost")
    public Post postAPost(@RequestBody Post post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();

        // получаем данные о пользователе для добавления его айдишника в поле автора
        User author = userService.findUserById(userDetails.getId());
        
        // Устанавливаем автора для поста
        post.setAuthor(author);
        return  postService.createPost(post);
    }

    @GetMapping("/myposts")
    public List<MyPostsDTO> getCurrentUserPosts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long currentUserId = userDetails.getId();
    
    return postService.findPostsByUserId(currentUserId).stream()
            .map(MyPostsDTO::fromEntity)
            .collect(Collectors.toList());
}

    @GetMapping("/{title}")
    public List<Post> getPostByTitle(@PathVariable String title) {
        return postService.getPostByTitle(title);
    }


    @DeleteMapping("/{postId}")
    public ResponseEntity<String>  deletePostById(@PathVariable Long postId,Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Long currentUserId = userDetails.getId();
        
        try{
            postService.deleteByAuthor(postId, currentUserId);
            return ResponseEntity.ok("post successfuly deleted");
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    
}
