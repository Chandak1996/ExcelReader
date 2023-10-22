package com.demo.excellerader.service;

import com.demo.excellerader.dto.SignUpRequest;

public interface AuthenticationService {
    String signup(SignUpRequest request);

    String signIn(String userName, String Password);
}
