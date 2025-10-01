package com.firstapp.blog.appblog.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.firstapp.blog.appblog.service.UserService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
    @Autowired
    private JwtCore jwtCore;
    @Autowired
    private UserService userDetailsService;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException ,IOException {
        String jwt = null;
        String username = null;
        UserDetails userDetails=null;
        UsernamePasswordAuthenticationToken auth = null;
        try{
            String headerAuth = request.getHeader("Authorization");
            if(headerAuth!=null && headerAuth.startsWith("Bearer ")){
                jwt = headerAuth.substring(7);
            }
            if(jwt!= null){
                try{
                    username = jwtCore.getUsernameFromToken(jwt);
                }catch(Exception e){
                    System.out.println("Your token expired: {}");
                }
                if(username != null&& SecurityContextHolder.getContext().getAuthentication()==null){
                    userDetails = userDetailsService.loadUserByUsername(username);

                    if(jwtCore.validateJwtToken(jwt)){
                        auth = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
                filterChain.doFilter(request,response);
            }
        }catch(Exception e){
            System.out.println("Cant set user authentication in security context");
        }
    }
    
}
