package com.firstapp.blog.appblog.UserDetails;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.firstapp.blog.appblog.model.Post;
import com.firstapp.blog.appblog.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails{
    private Long id;
    private String username;
    private String password;
    private Set<Post> posts;

    public static UserDetailsImpl build(User user){
        Set<Post> posts = user.getPosts();

        return new UserDetailsImpl(
            user.getId(),
            user.getUsername(),
            user.getPassword(),
            posts);

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    }
}
