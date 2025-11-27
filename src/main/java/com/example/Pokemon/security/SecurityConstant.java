package com.example.Pokemon.security;


import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class SecurityConstant {
    public static final long JWT_EXPIRATION = 7000;

    // Generate a secure HS512 key
    public static final Key JWT_SECRET = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);
}
