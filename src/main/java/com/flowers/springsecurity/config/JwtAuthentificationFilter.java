package com.flowers.springsecurity.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthentificationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException
    {
        // 1. Extract the Authorization header from the request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 2. Check if the header is missing or doesn't start with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // If no JWT, move to the next filter in the chain (e.g., Username/Password filter)
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract the token (everything after "Bearer ")
        jwt = authHeader.substring(7);

        // 4. Extract the username/email from the JWT token
        userEmail = jwtService.extractUsername(jwt);

        // 5. If we have an email and the user is NOT already authenticated in the SecurityContext
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // 6. Load the user details from the database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            
            // 7. Check if the token is still valid (not expired and belongs to this user)
            if (jwtService.isTokenValid(jwt, userDetails)) {
                
                // 8. Create an Authentication object for Spring Security
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // We don't need credentials (password) since JWT is already verified
                        userDetails.getAuthorities()
                );
                
                // 9. Enforce details from the request (IP address, session ID, etc.)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                
                // 10. Update the Security Context - The user is now officially "logged in" for this request
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // 11. Always continue the filter chain
        filterChain.doFilter(request, response);
    }
}
