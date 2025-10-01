package com.firstapp.blog.appblog.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.firstapp.blog.appblog.UserDetails.UserDetailsImpl;
import com.firstapp.blog.appblog.model.User;
import com.firstapp.blog.appblog.repos.UserRepository;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(
            String.format("User '%s' not found",username)
        ));
        
        return UserDetailsImpl.build(user);
    }
    
}
