package com.xhaven.xhavenserver.service;

import com.xhaven.xhavenserver.dto.auth.AuthenticationResponseDto;
import com.xhaven.xhavenserver.dto.auth.LoginRequestDto;
import com.xhaven.xhavenserver.model.entity.Token;
import com.xhaven.xhavenserver.model.TokenType;
import com.xhaven.xhavenserver.config.security.CustomUserDetails;
import com.xhaven.xhavenserver.config.security.JwtService;
import com.xhaven.xhavenserver.repository.RoleRepository;
import com.xhaven.xhavenserver.repository.TokenRepository;
import com.xhaven.xhavenserver.dto.auth.RegisterRequestDto;
import com.xhaven.xhavenserver.model.RoleEnum;
import com.xhaven.xhavenserver.model.entity.Role;
import com.xhaven.xhavenserver.model.entity.User;
import com.xhaven.xhavenserver.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponseDto register(RegisterRequestDto registerRequestDto) {
        User newUser = User.builder()
                .name(registerRequestDto.getName())
                .surname(registerRequestDto.getSurname())
                .email(registerRequestDto.getEmail())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .roles(Set.of(roleRepository.findByName(RoleEnum.ROLE_USER).orElseThrow(
                        () -> new IllegalArgumentException("Invalid role")
                )))
                .build();

        User savedUser = userRepository.save(newUser);

        String jwtToken = jwtService.generateToken(new CustomUserDetails(newUser));
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User with this email not found!"));
        String jwtToken = jwtService.generateToken(new CustomUserDetails(user));
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public void logout() {
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        revokeAllUserTokens(principal.getCurrentUser());
        SecurityContextHolder.clearContext();
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
