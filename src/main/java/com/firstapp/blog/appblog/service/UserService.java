package com.firstapp.blog.appblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.firstapp.blog.appblog.UserDetails.UserDetailsImpl;
import com.firstapp.blog.appblog.model.User;
import com.firstapp.blog.appblog.repos.UserRepository;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(
            String.format("User '%s' not found",username)
        ));
        
        return UserDetailsImpl.build(user);
    }

    public User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    
    public User registerNewUser(String username, String password){
        if(userRepository.findByUsername(username).isPresent()){
            throw new RuntimeException("Username already exists!");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        

        return userRepository.save(user);
    }
    
}
