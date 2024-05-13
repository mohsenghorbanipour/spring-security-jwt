package com.devpro.jwt.controllers;

import com.devpro.jwt.dto.ReqRes;
import com.devpro.jwt.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/signup")
    public ResponseEntity<ReqRes> signup(@RequestBody ReqRes request) {
            return ResponseEntity.ok(service.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<ReqRes> signIn(@RequestBody ReqRes request) {
        return ResponseEntity.ok(service.signIn(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ReqRes> refresh(@RequestBody ReqRes request) {
        return ResponseEntity.ok(service.refreshToken(request));
    }
}
