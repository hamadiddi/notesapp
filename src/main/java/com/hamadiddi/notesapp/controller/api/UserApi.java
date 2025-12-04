package com.hamadiddi.notesapp.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hamadiddi.notesapp.dto.UserLoginDto;
import com.hamadiddi.notesapp.dto.UsersReqDto;


@RequestMapping("/api/auth")
public interface UserApi {

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsersReqDto user);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto user);


}
