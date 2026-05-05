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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
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

        // 5. store role in request
        request.setAttribute("userRole", role);
        request.setAttribute("logged", true);

        // get auth user data
        // Fetch user from token
        String email = jwtUtils.getEmailFromToken(token);
        Optional<UserAuth> userOpt = userAuthRepository.findUserAuthByEmail(email);
        if (userOpt.isEmpty()) {
            throw new UnauthorizedException("User not found");
        }

        // Store current user in request attribute for controllers
        request.setAttribute("currentUser", userOpt.get());

        // 6. request
        filterChain.doFilter(request, response);

    }
}
