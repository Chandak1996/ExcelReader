package com.demo.excellerader.controller;

import com.demo.excellerader.dto.SignUpRequest;
import com.demo.excellerader.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authService;
    @PostMapping("/login")
    ResponseEntity<String> userLogin(@RequestParam String userName, @RequestParam String password){
        //System.out.println("UserName : "+userName+" "+"Password : "+password);
        return ResponseEntity.ok(authService.signIn(userName,password));
    }

    @PostMapping("/signup")
    ResponseEntity<String> userSignup(@RequestBody SignUpRequest signUpRequest){

        return ResponseEntity.ok(authService.signup(signUpRequest));
    }

}
