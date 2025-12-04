package com.hamadiddi.notesapp.controller.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import com.hamadiddi.notesapp.controller.api.UserApi;
import com.hamadiddi.notesapp.model.Users;
import com.hamadiddi.notesapp.repository.UserRepository;
import com.hamadiddi.notesapp.utils.Response;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "User Management", description = "Endpoints for user management")
public class UserController implements UserApi{

    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

@Override
public ResponseEntity<?> register(Users user) {

    Response<Users> response;

    // Validate null/empty
    if (user == null ||
        user.getUsername() == null || user.getUsername().isEmpty() ||
        user.getPassword() == null || user.getPassword().isEmpty()) {

        response = new Response<>(
                "Username or password cannot be null or empty",
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

    // Encode password
    user.setPassword(encoder.encode(user.getPassword()));

    // Save user
    Users saved = userRepository.save(user);

    response = new Response<>(
            "Registration successful",
            "success",
            saved,
            200
    );

    return ResponseEntity.ok(response);
}



    

}
