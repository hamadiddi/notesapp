package com.hamadiddi.notesapp.controller.web;


import java.util.HashMap;
import java.util.Map;

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
import com.hamadiddi.notesapp.utils.ErrorResponse;
import com.hamadiddi.notesapp.utils.SuccessResponse;

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

    

    // Validate null/empty
    if (user == null ||
        user.getUsername() == null || user.getUsername().isEmpty() ||
        user.getPassword() == null || user.getPassword().isEmpty() ||
        user.getEmail() == null || user.getEmail().isEmpty()) {

        ErrorResponse response = new ErrorResponse(
                "fail",
                "Username or password or email cannot be null or empty"
        );

        return ResponseEntity.status(400).body(response);
    }

    // Check for existing username
    if (userRepository.findByUsername(user.getUsername()).isPresent()) {

        ErrorResponse response = new ErrorResponse(
                "fail",
                "Username already exists"
                
        );

        return ResponseEntity.status(409).body(response);
    }

    // Check for existing username
    if (userRepository.findByEmail(user.getEmail()).isPresent()) {

        ErrorResponse response = new ErrorResponse(
                "fail",
                "Email already exists"
                
                
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

    SuccessResponse<Users> response = new SuccessResponse<>(
            "success",
            "Registration successful",            
            saved
    );

    return ResponseEntity.ok(response);
}


public ResponseEntity<?> login(UserLoginDto user) {
    Map<String, Object> resp = new HashMap<>();
    
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    if (authentication.isAuthenticated()) {
        resp.put("token", jwtService.generateToken(user.getUsername()));
        return ResponseEntity.ok(resp);
    }
    return ResponseEntity.ok("fail"); 
}
    

}
