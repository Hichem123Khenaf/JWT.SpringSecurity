package com.training.spring.security.jwtspringsecurity.filter;

import com.training.spring.security.jwtspringsecurity.security.SecurityConstants;
import com.training.spring.security.jwtspringsecurity.utility.JWTTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private JWTTokenProvider jwtTokenProvider;

    public JWTAuthorizationFilter(JWTTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase(SecurityConstants.OPTION_HTTP_METHODS)) {
            response.setStatus(HttpStatus.OK.value());
        }else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(authorizationHeader == null || authorizationHeader.startsWith(SecurityConstants.TOKEN_HEADER)){
                filterChain.doFilter(request,response);
                return;
            }
            String token = authorizationHeader.substring(SecurityConstants.TOKEN_HEADER.length());
            String username = jwtTokenProvider.getSubject(token);

            if (jwtTokenProvider.isTokenValid(username, token) && SecurityContextHolder.getContext().getAuthentication() == null){

                  List<GrantedAuthority> authorityList = jwtTokenProvider.getAuthorities(token);
                  Authentication authentication = jwtTokenProvider.getAuthentication(username, authorityList,request);


            }


        }
    }
}
