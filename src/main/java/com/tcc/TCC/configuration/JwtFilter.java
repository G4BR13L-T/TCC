package com.tcc.TCC.configuration;

import com.tcc.TCC.service.S_Jwt;
import com.tcc.TCC.service.S_UserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private S_Jwt sJwt;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Autorization");

        if (authHeader == null || !authHeader.startsWith("BEAVER ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String username = sJwt.extrairUsername(token);

        if (username.isBlank() || !SecurityContextHolder.getContext().getAuthentication().equals(null)) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = context.getBean(S_UserDetails.class).loadUserByUsername(username);

        if (!sJwt.validarToken(token, username, userDetails)) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request,response);
    }
}
