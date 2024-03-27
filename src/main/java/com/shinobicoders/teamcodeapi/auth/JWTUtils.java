package com.shinobicoders.teamcodeapi.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JWTUtils {
    @Value("${auth.jwt.secret}")
    private String secret;

    @Value("${auth.jwt.ttl}")
    private Long ttl;

    public String issueToken(String userId, String email, String roles) {
        var now = Instant.now();
        return JWT.create()
                .withSubject(userId)
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(ttl))
                .withClaim("email", email)
                .withClaim("roles", roles)
                .sign(Algorithm.HMAC256(secret));
    }

    public DecodedJWT decodeToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token);
    }

    public UserPrincipal convertTokenToPrincipal(DecodedJWT jwt) {
        //var rolesList = getClaimOrEmptyList(jwt, "roles").stream()
          //      .map(SimpleGrantedAuthority::new)
            //    .toList();

        return UserPrincipal.builder()
                .userId(Long.parseLong(jwt.getSubject()))
                .email(jwt.getClaim("email").asString())
                .authorities(null)
                .build();
    }

    private List<String> getClaimOrEmptyList(DecodedJWT jwt, String claim) {
        if (jwt.getClaim(claim).isNull()) return List.of();
        return jwt.getClaim(claim).asList(String.class);
    }
}
