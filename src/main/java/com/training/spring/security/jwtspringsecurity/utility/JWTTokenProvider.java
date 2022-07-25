package com.training.spring.security.jwtspringsecurity.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.training.spring.security.jwtspringsecurity.model.UserPrincipal;
import com.training.spring.security.jwtspringsecurity.security.SecurityConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JWTTokenProvider {

    @Value("${jjwt.secret}")
    private String secret;

    // générer token pour clients

    private String generateJWTToken(UserPrincipal userPrincipal) {
        String[] claims = getClaimsForUser(userPrincipal);
        return JWT.create().withIssuer(SecurityConstants.GET_ARRAYS_LLC).withAudience(SecurityConstants.GET_ARRAYS_ADMINISTRATION)
                .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername())
                .withArrayClaim(SecurityConstants.AUTHORITIES, claims).withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    // get authorities, nous allons récupérer les claims de token pour savoir les roles.
    public List<SimpleGrantedAuthority> getAuthorities(String token) {
        String[] claims = getClaimsForToken(token);
        return Arrays.stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    }

    public Authentication getAuthentication(String user, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, authorities);

        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return usernamePasswordAuthenticationToken;
    }

    public boolean isTokenValid(String user, String token) {
        JWTVerifier jwtVerifier = getJwtVerifier();
        return StringUtils.isNoneEmpty(user) && isTokenExpired(jwtVerifier, token);

    }

    public String getSubject(String token) {

        JWTVerifier jwtVerifier = getJwtVerifier();
        return  jwtVerifier.verify(token).getSubject();
    }
    private boolean isTokenExpired(JWTVerifier jwtVerifier, String token) {

        Date expiresAt = jwtVerifier.verify(token).getExpiresAt();
        return expiresAt.before(new Date());
    }

    // get claims de notre user
    private String[] getClaimsForUser(UserPrincipal user) {
        List<String> authorities = new ArrayList<>();
        user.getAuthorities().forEach(grantedAuthority -> {
            authorities.add(grantedAuthority.getAuthority());
        });
        return authorities.toArray(String[]::new);
    }

    // get claims for token, nous devons avant tous vérifier cette token avec get vérifier methode.
    private String[] getClaimsForToken(String token) {
        JWTVerifier jwtVerifier = getJwtVerifier();
        return jwtVerifier.verify(token).getClaim(SecurityConstants.AUTHORITIES).asArray(String.class);
    }

    // pour Vérifier est ce que cette token est crypter avec l'algorithme HMAC512
    private JWTVerifier getJwtVerifier() {
        JWTVerifier jwtVerifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            jwtVerifier = JWT.require(algorithm).withIssuer(SecurityConstants.GET_ARRAYS_LLC).build();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException(SecurityConstants.TOKEN_CANNOT_BE_VERIFIED);
        }
        return jwtVerifier;
    }


}
