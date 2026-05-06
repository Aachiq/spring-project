package com.example.restapi.filter;

import com.example.restapi.exception.UnauthorizedException;
import com.example.restapi.model.UserAuth;
import com.example.restapi.repository.UserAuthRepository;
import com.example.restapi.security.JwtUtils;
import com.example.restapi.service.UserAuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserAuthRepository userAuthRepository;

    public JwtFilter(JwtUtils jwtUtils, UserAuthRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.userAuthRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // 1. public routes
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extract token
        String token = authHeader.substring(7);

        // 3. Validate token
        if (!jwtUtils.validateToken(token)) {
            throw new UnauthorizedException("Invalid or expired token");
        }

        // 4. Extract role
        String role = jwtUtils.extractRole(token);

        // Fetch user from token
        String email = jwtUtils.getEmailFromToken(token);
        Optional<UserAuth> userOpt = userAuthRepository.findUserAuthByEmail(email);

        if (userOpt.isEmpty()) {
            throw new UnauthorizedException("User not found");
        }
        UserAuth currentUser = userOpt.get();

        // Create authorities (roles)
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

        // Create authentication object
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null, authorities);

        // THIS IS THE MOST IMPORTANT PART
        SecurityContextHolder.getContext().setAuthentication(auth);

        // 6. request
        filterChain.doFilter(request, response);

    }
}
