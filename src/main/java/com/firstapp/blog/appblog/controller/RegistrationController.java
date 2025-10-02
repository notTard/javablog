package com.firstapp.blog.appblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.firstapp.blog.appblog.controller.DTO.LogInRequest;
import com.firstapp.blog.appblog.controller.DTO.RegistrationRequest;
import com.firstapp.blog.appblog.jwt.JwtCore;
import com.firstapp.blog.appblog.model.User;
import com.firstapp.blog.appblog.repos.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    private JwtCore jwtCore;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        // Проверяем, не существует ли уже пользователь
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        // Создаем нового пользователя
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // Сохраняем пользователя
        userRepository.save(user);
        
        // Генерируем JWT токен
        
        return ResponseEntity.ok().body("User registered successfully. " );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LogInRequest logInRequest) {
        System.out.println("Received: " + logInRequest.getUsername() + ", " + logInRequest.getPassword());
        Authentication authentication = null;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(logInRequest.getUsername(),logInRequest.getPassword()));
        }catch(BadCredentialsException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtCore.generateToken(authentication);
        return ResponseEntity.ok("Logged successfully tokon :"+ token);
    }
}
