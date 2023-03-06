package com.xhaven.xhavenserver.service;

import com.xhaven.xhavenserver.dto.AuthenticationResponse;
import com.xhaven.xhavenserver.dto.LoginRequest;
import com.xhaven.xhavenserver.model.entity.Token;
import com.xhaven.xhavenserver.model.TokenType;
import com.xhaven.xhavenserver.config.security.CustomUserDetails;
import com.xhaven.xhavenserver.config.security.JwtService;
import com.xhaven.xhavenserver.repository.TokenRepository;
import com.xhaven.xhavenserver.dto.RegisterRequest;
import com.xhaven.xhavenserver.model.RoleEnum;
import com.xhaven.xhavenserver.model.entity.Role;
import com.xhaven.xhavenserver.model.entity.User;
import com.xhaven.xhavenserver.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User newUser = User.builder()
                .name(registerRequest.getName())
                .surname(registerRequest.getSurname())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(Set.of(new Role(RoleEnum.ROLE_USER)))
                .build();

        User savedUser = userRepository.save(newUser);

        String jwtToken = jwtService.generateToken(new CustomUserDetails(newUser));
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User with this email not found!"));
        String jwtToken = jwtService.generateToken(new CustomUserDetails(user));
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void logout() {

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
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
