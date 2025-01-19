package com.adebisi.student_management.config.jwt;


import com.adebisi.student_management.config.security.CustomerUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtService {

    @Value("${application.security.jwt.secret_key:}")
    private String secretKey;

    @Value("${token.expiry.date:}")
    private Integer time;

    public String extractUsername(String token)  {
        final Claims claims = extractAllClaims(token);

        log.info("here is the subject: {}",claims.getSubject() );
        return (String) claims.get("email");
    }

    public String generateToken(CustomerUserDetails userDetails) {

        Map<String, Object> claims =  new HashMap<>();
        claims.put("role", userDetails.getRole().toString());
        claims.put("username", userDetails.getUsername());
        claims.put("email", userDetails.getEmail());

        return Jwts.builder()
                .setSubject(userDetails.getEmail())
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +   time))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {

        final Claims claims = extractAllClaims(token);
       // String email = (String) claims.get("email");


        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {

            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

    }


    public boolean isTokenValid(String token, CustomerUserDetails userDetails)  {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getEmail())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {


        return extractClaims(token, Claims::getExpiration).before(new Date());
    }


}
