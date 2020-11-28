package com.markorusic.webstore.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.markorusic.webstore.security.domain.AuthResponseDto;
import com.markorusic.webstore.security.domain.AuthUser;
import com.markorusic.webstore.security.exception.UnauthorizedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
public class AuthService implements InitializingBean {

    @Value("${jwt.token-duration}")
    private Long TOKEN_DURATION;

    @Value("${jwt.secret}")
    private String SECRET;

    @Autowired
    private ObjectMapper objectMapper;

    private final Logger log = LoggerFactory.getLogger(AuthService.class);

    private AuthUser user;

    private SecretKey key;

    @Override
    public void afterPropertiesSet() throws Exception {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    public void init(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
            user = objectMapper.readValue(claims.getSubject(), AuthUser.class);
        } catch (JsonProcessingException e) {
            throw new UnauthorizedException();
        }
    }

    public AuthUser getUser() {
        return user;
    }

    public <T> AuthResponseDto authorize(AuthUser authUser, T user) {
        var now = (new Date()).getTime();
        var validity = new Date(now + TOKEN_DURATION);

        try {
            var jws = Jwts
                    .builder()
                    .setSubject(objectMapper.writeValueAsString(authUser))
                    .signWith(key)
                    .setExpiration(validity)
                    .compact();
            return new AuthResponseDto(jws, user);
        } catch (JsonProcessingException e) {
            throw new UnauthorizedException();
        }

    }
}
