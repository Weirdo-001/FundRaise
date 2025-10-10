// File: src/main/java/com/example/fundRaise/JwtAuthFilter.java (Corrected with Modern API)
package com.example.fundRaise;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;
    private final Key jwtSigningKey;

    // Use constructor injection for the key for consistency and immutability
    public JwtAuthFilter(CustomUserDetailsService customUserDetailsService, @Value("${app.jwt.secret}") String jwtSecret) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtSigningKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        try {
            // **FIXED**: Using the modern, non-deprecated parserBuilder API
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSigningKey)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            userEmail = claims.getSubject();

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(userEmail);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            logger.warn("JWT token processing error: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}

