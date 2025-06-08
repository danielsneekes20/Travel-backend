package com.Sneekes.backend.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;

        // 🔍 Extract token from cookie named "jwt"
        System.out.println("🍪 Cookie ontvangen: " + request.getCookies());

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println("🍪 Cookie ontvangen: " + cookie.getName() + " = " + cookie.getValue());
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                    logger.debug("JWT token extracted from cookie");
                    break;
                }
            }
        }

        if (token == null) {
            logger.debug("JWT cookie not found");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.extractUsername(token);
                logger.debug("Token is valid. Extracted username: {}", username);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    logger.debug("Successfully authenticated user: {}", username);
                } else {
                    logger.warn("UserDetailsService returned null for username: {}", username);
                }
            } else {
                logger.debug("JWT token validation failed");
            }
        } catch (Exception e) {
            logger.error("Exception while validating JWT token: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
