package com.demo.excellerader.service.implementation;

import com.demo.excellerader.dto.SignUpRequest;
import com.demo.excellerader.model.Role;
import com.demo.excellerader.model.User;
import com.demo.excellerader.repository.UserRepository;
import com.demo.excellerader.service.AuthenticationService;
import com.demo.excellerader.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public String signup(SignUpRequest request) {
         User user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        userRepository.save(user);
        String jwt = jwtService.generateJwtToken(user.getUsername());
        return jwt;
    }

    @Override
    public String signIn(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        String jwt = jwtService.generateJwtToken(user.getUsername());
        return jwt;
    }
}
