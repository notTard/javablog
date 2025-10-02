package com.firstapp.blog.appblog.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.firstapp.blog.appblog.UserDetails.UserDetailsImpl;
import com.firstapp.blog.appblog.model.Post;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtCore {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private int jwtExpiration;

    private Key key;
    
    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication){
        UserDetailsImpl  userDetails = (UserDetailsImpl)authentication.getPrincipal();//Для ролей в будущем (Я ОТВЕЧАЮ)

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiration))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }
    public boolean validateJwtToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (SecurityException |
                MalformedJwtException | 
                ExpiredJwtException | 
                UnsupportedJwtException | 
                IllegalArgumentException e){

            return false;
        }
    }

    public String getUsernameFromToken(String token){
        return Jwts.parserBuilder()
                    .setSigningKey(key).build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
    }


}
