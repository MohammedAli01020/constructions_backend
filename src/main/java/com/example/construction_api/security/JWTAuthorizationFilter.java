package com.example.construction_api.security;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


import static com.example.construction_api.security.SecurityConstants.*;
import static java.util.Arrays.stream;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(header);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAuthentication(String header) {
        if (header != null) {


            // parse the token.
//            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
//                    .build()
//                    .verify(header.replace(TOKEN_PREFIX, ""))
//                    .getSubject();

            String token  = header.substring(TOKEN_PREFIX.length());
            Algorithm algorithm = Algorithm.HMAC512(SECRET.getBytes());
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();

            DecodedJWT decodedJWT = jwtVerifier.verify(token);

            String username = decodedJWT.getSubject();

            String[] roles =  decodedJWT.getClaim(SecurityConstants.ROLES_CLAIM).asArray(String.class);

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

            stream(roles).forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role));
            });

            return username != null ? new UsernamePasswordAuthenticationToken(username, null, authorities) : null;
        }
        return null;
    }
}