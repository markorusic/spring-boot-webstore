package com.markorusic.webstore.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.markorusic.webstore.security.domain.AuthResponseDto;
import com.markorusic.webstore.security.domain.AuthUser;
import com.markorusic.webstore.security.exception.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService implements InitializingBean {

    @Value("${jwt.token-duration-hours}")
    private Long TOKEN_DURATION_HOURS;

    @Value("${jwt.secret}")
    private String SECRET;

    private final ObjectMapper objectMapper;

    private SecretKey key;

    @Override
    public void afterPropertiesSet() {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    public void init(String token) {
        try {
            var claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
            var user = objectMapper.readValue(claims.getSubject(), AuthUser.class);
            var authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } catch (JsonProcessingException e) {
            throw new UnauthorizedException();
        }
    }

    public AuthUser getUser() {
        return (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public <T> AuthResponseDto authorize(AuthUser authUser, T user) {
        var now = (new Date()).getTime();
        var validity = new Date(now + TOKEN_DURATION_HOURS * 60 * 60 * 1000);

        try {
            var jws = Jwts
                    .builder()
                    .setSubject(objectMapper.writeValueAsString(authUser))
                    .signWith(key)
                    .setExpiration(validity)
                    .compact();
            return AuthResponseDto.builder().token(jws).user(user).build();
        } catch (JsonProcessingException e) {
            throw new UnauthorizedException();
        }

    }
}
