package com.example.Pokemon.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTGenerator {
    public String generateToken(Authentication authentication){
        String username=authentication.getName();
        Date currentDate=new Date();
        Date expiryDate=new Date(currentDate.getTime()+SecurityConstant.JWT_EXPIRATION);

        String token= Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SECRET)
                .compact();
        return token;
    }

    public String getUserNameFromJWT(String token){
        Claims claim=Jwts.parser()
                .setSigningKey(SecurityConstant.JWT_SECRET)
                .parseClaimsJwt(token)
                .getBody();

        return claim.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(SecurityConstant.JWT_SECRET)
                    .parseClaimsJwt(token);
            return true;
        } catch (Exception exception){
            throw new AuthenticationCredentialsNotFoundException("JWT was Expired or incorrect");
        }
    }
}
