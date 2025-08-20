package com.tcc.TCC.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class S_Jwt {
    private final String secretKey;

    public S_Jwt() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        SecretKey key = keyGen.generateKey();
        this.secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public String generateToken(String username) {
        Map<String,Object> claims = new HashMap<>();

        Date agora = new Date();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(agora)
                .expiration(new Date(agora.getTime() + 1000 * 60 * 20))
                .and().signWith(getKey())
                .compact();
    }

    public SecretKey getKey () {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extrairUsername (String token) {
        return extrairDeClaim(token, Claims::getSubject);
    }

    private Date extractExpiration (String token) {
        return extrairDeClaim(token, Claims::getExpiration);
    }

    public boolean validarToken(String token, String username, UserDetails userDetails){
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private <T> T extrairDeClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims (String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired (String token){
        return extractExpiration(token).before(new Date());
    }
}
