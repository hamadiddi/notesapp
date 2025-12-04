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

@RestController
public class UserController implements UserApi{

    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

@Override
public ResponseEntity<?> register(Users user) {

    Response<Users> response = new Response<>();
    
    // Validate input (optional but recommended)
    if (user.getUsername().equals("") || user == null || user.getPassword() == null || user.getPassword().equals("")) {
        response = new Response<>(
            "User or password cannot be null or empty", "fail", null, 400
        );
        return ResponseEntity.ok(response);
    }

    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
        return ResponseEntity.ok(response = new Response<>(
            "The username already exists", "fail", null, 409
        ));
    }
    // Encode password
    user.setPassword(encoder.encode(user.getPassword()));

    // Save and return the saved entity
    return ResponseEntity.ok(userRepository.save(user));
}


    

}
