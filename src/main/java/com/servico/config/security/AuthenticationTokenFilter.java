package com.servico.config.security;

import com.servico.model.entity.User;
import com.servico.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserService userService;

    public AuthenticationTokenFilter(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getRequestToken(request);
        if (tokenService.validateToken(token))
            authenticateUser(token);
        filterChain.doFilter(request, response);
    }

    private String getRequestToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization"), tokenType = "Bearer ";
        if (token == null || token.isEmpty() || !token.startsWith(tokenType))
            return null;
        return token.substring(tokenType.length());
    }

    private void authenticateUser(String token) {
        Long userId = tokenService.getUserIdByToken(token);
        User user = userService.getUserById(userId);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getLogin(), null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
