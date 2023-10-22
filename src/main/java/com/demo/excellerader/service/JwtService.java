package com.demo.excellerader.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    public Long getLoggedInUserId();
    public String generateJwtToken(String userName);
    public String getUserNameFromJwtToken(String token);
    public boolean validateJwtToken(String authToken);

}
