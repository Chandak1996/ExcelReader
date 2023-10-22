package com.demo.excellerader.service.implementation;

import com.demo.excellerader.model.User;
import com.demo.excellerader.service.JwtService;
import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    private static final Logger LOGGER = LogManager.getLogger(JwtServiceImpl.class);

    // private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${excelreader.app.jwtSecret}")
    private String jwtSecret;

    @Value("${excelreader.app.jwtExpirationMs}")
    private Long jwtExpirationMs;

    public Long getLoggedInUserId() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authenticationToken.getPrincipal();
        return userDetails.getId();
    }

    public String generateJwtToken(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        boolean isValid = false;
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            isValid = (!claims.getBody().getExpiration().before(new Date()));
            LOGGER.info("validateToken: isValid : " + isValid);
        } catch (SignatureException e) {
            LOGGER.error("validateJwtToken:" + e, e);
        } catch (MalformedJwtException e) {
            LOGGER.error("validateJwtToken:" + e, e);
        } catch (ExpiredJwtException e) {
            LOGGER.error("validateJwtToken:" + e, e);
        } catch (UnsupportedJwtException e) {
            LOGGER.error("validateJwtToken:" + e, e);
        } catch (IllegalArgumentException e) {
            LOGGER.error("validateJwtToken:" + e, e);
        }
        return isValid;
    }
}
