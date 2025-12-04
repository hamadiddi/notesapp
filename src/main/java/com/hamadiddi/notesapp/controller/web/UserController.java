package com.hamadiddi.notesapp.controller.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import com.hamadiddi.notesapp.controller.api.UserApi;
import com.hamadiddi.notesapp.dto.UserLoginDto;
import com.hamadiddi.notesapp.dto.UsersReqDto;
import com.hamadiddi.notesapp.model.Users;
import com.hamadiddi.notesapp.repository.UserRepository;
import com.hamadiddi.notesapp.service.JWTService;
import com.hamadiddi.notesapp.utils.Response;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "User Management", description = "Endpoints for user management")
public class UserController implements UserApi{

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

@Override
public ResponseEntity<?> register(UsersReqDto user) {

    Response<Users> response;

    // Validate null/empty
    if (user == null ||
        user.getUsername() == null || user.getUsername().isEmpty() ||
        user.getPassword() == null || user.getPassword().isEmpty() ||
        user.getEmail() == null || user.getEmail().isEmpty()) {

        response = new Response<>(
                "Username or password or email cannot be null or empty",
                "fail",
                null,
                400
        );

        return ResponseEntity.status(400).body(response);
    }

    // Check for existing username
    if (userRepository.findByUsername(user.getUsername()).isPresent()) {

        response = new Response<>(
                "Username already exists",
                "fail",
                null,
                409
        );

        return ResponseEntity.status(409).body(response);
    }

    // Check for existing username
    if (userRepository.findByEmail(user.getEmail()).isPresent()) {

        response = new Response<>(
                "Email already exists",
                "fail",
                null,
                409
        );

        return ResponseEntity.status(409).body(response);
    }

    // Encode password
    user.setPassword(encoder.encode(user.getPassword()));

    // Convert DTO to entity
    Users entity = new Users();
    entity.setUsername(user.getUsername());
    entity.setPassword(user.getPassword());
    entity.setEmail(user.getEmail());

    // Save user
    Users saved = userRepository.save(entity);

    response = new Response<>(
            "Registration successful",
            "success",
            saved,
            200
    );

    return ResponseEntity.ok(response);
}


public String login(UserLoginDto user) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    if (authentication.isAuthenticated()) {
        return jwtService.generateToken(user.getUsername());
    }
    return "fail"; 
}
    

}
