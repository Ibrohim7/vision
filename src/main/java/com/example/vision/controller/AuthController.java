package com.example.vision.controller;

import com.example.vision.controller.models.Response;
import com.example.vision.controller.models.Status;
import com.example.vision.entity.User;
import com.example.vision.payload.ReqSignIn;
import com.example.vision.payload.ReqSignUp;
import com.example.vision.repository.UserRepository;
import com.example.vision.security.JwtTokenProvider;
import com.example.vision.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;


    @Value("604800000")
    private long accessTokenDate;

    @Autowired
    public AuthController(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, ObjectMapper objectMapper, UserServiceImpl userServiceImpl, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.userServiceImpl = userServiceImpl;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody ReqSignIn reqSignIn) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ObjectNode data = objectMapper.createObjectNode();
        Optional<User> optionalUser = userRepository.findByUsernameAndActive(reqSignIn.getUsername(), true);
        Status status;

        if (optionalUser.isPresent()) {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(reqSignIn.getUsername(), reqSignIn.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtTokenProvider.generateToken(authentication);
            String refreshToken = jwtTokenProvider.refreshToken(authentication);
            data.put("accessToken", jwt);
            data.put("refreshToken", refreshToken);
            data.put("tokenType", "Bearer ");
            data.put("expiryDate", accessTokenDate);
            status = new Status(0, "Success");
        } else {
            status = new Status(404, "User not found");
        }

        return ResponseEntity.ok(new Response(data, status));
    }

    @PostMapping("/create")
    public HttpEntity<?> signUp(@RequestBody ReqSignUp reqSignUp) {
        Response response = userServiceImpl.signUp(reqSignUp);
        return ResponseEntity.ok(response);

    }
}