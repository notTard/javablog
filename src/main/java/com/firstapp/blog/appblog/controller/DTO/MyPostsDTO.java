package com.firstapp.blog.appblog.controller.DTO;

import com.firstapp.blog.appblog.model.Post;

import lombok.Data;

@Data
public class MyPostsDTO {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private String authorName;

    public static MyPostsDTO fromEntity(Post post) {
        MyPostsDTO dto = new MyPostsDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setAuthorId(post.getAuthor().getId());
        dto.setAuthorName(post.getAuthor().getUsername());
        return dto;
    }
}