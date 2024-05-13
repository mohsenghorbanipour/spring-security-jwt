package com.devpro.jwt.services;

import com.devpro.jwt.dto.ReqRes;
import com.devpro.jwt.entities.User;
import com.devpro.jwt.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ReqRes signup(ReqRes registrationRequest) {
        ReqRes res = new ReqRes();
        try {
            User user = new User();
            user.setEmail(registrationRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setRole(registrationRequest.getRole());
            User saveUser = userRepo.save(user);
            if(saveUser != null) {
                res.setUser(saveUser);
                res.setMessage("User Create Successfully");
                res.setStatusCode(200);

            }
        } catch (Exception e) {
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
        }
        return  res;
    }

    public ReqRes signIn(ReqRes signInRequest) {
        ReqRes res = new ReqRes();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
            var user = userRepo.findByEmail(signInRequest.getEmail()).orElseThrow();
            System.out.println("USER : " + user);
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            res.setStatusCode(200);
            res.setToken(jwt);
            res.setRefreshToken(refreshToken);
            res.setStatusCode(200);
            res.setExpirationTime("24Hr");
            res.setMessage("Successfully Signed In");
        } catch (Exception e) {
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
        }
        return  res;
    }

    public  ReqRes refreshToken(ReqRes refreshTokenRequest) {
        ReqRes res = new ReqRes();
        try {
            String email = jwtUtils.extractUsername(refreshTokenRequest.getToken());
            User user = userRepo.findByEmail(email).orElseThrow();
            if(jwtUtils.isTokenValid(refreshTokenRequest.getToken(), user)) {
                var jwt = jwtUtils.generateToken(user);
                res.setStatusCode(200);
                res.setToken(jwt);
                res.setRefreshToken(res.getRefreshToken());
                res.setExpirationTime("24Hr");
                res.setMessage("Successfully Refreshed Token");
            }
        } catch (Exception e) {
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
        }
        return  res;
    }

}
