package com.firstapp.blog.appblog.controller.DTO;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String username;
    private String password;
}