package com.example.springsecurityjwtfinalpractise.auth.AuthenticationControlle;

import com.example.springsecurityjwtfinalpractise.config.JwtService;
import com.example.springsecurityjwtfinalpractise.model.Role;
import com.example.springsecurityjwtfinalpractise.model.Users;
import com.example.springsecurityjwtfinalpractise.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo repo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = Users.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        System.out.println(request.getPassword());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));


        var user =repo.findByEmail(request.getEmail()).orElseThrow(()->new RuntimeException("The code is not running"));
        var jwtToken= jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }
}

