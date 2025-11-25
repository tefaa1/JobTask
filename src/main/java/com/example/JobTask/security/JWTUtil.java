package com.example.JobTask.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;

    private Key key;

    @PostConstruct
    public void init(){
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateAccessToken(String email){

        return Jwts.builder()
                .setSubject("access")
                .claim("email",email)
                .setIssuer("Mohamed Abdellatif")
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(15,ChronoUnit.MINUTES)))
                .signWith(key)
                .compact();
    }

    private String generateRefreshToken(String email){

        return Jwts.builder()
                .setSubject("refresh")
                .claim("email",email)
                .setIssuer("Mohamed Abdellatif")
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)))
                .signWith(key)
                .compact();
    }

    public Boolean isTokenValid(String token){
        try{
            Claims claims = parseToken(token);
            return claims.getExpiration().after(new Date());
        }
        catch (JwtException exception){
            return false;
        }
    }

    public String extractEmail(String token){

        Claims claims = parseToken(token);
        return claims.get("email",String.class);
    }

    public Boolean isAccessToken(String token){
         Claims claims = parseToken(token);
         return "access".equals(claims.getSubject());
    }

    private Claims parseToken(String token){

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
