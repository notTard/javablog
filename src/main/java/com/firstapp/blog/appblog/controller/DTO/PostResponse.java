package com.firstapp.blog.appblog.controller.DTO;

import com.firstapp.blog.appblog.model.Post;

import lombok.Data;

@Data
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private Long authorId; // Только ID автора вместо целого объекта
    
    public static PostResponse fromEntity(Post post) {
        PostResponse dto = new PostResponse();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setAuthorId(post.getAuthor().getId());
        return dto;
    }
}
