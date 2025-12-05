package com.hamadiddi.notesapp.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hamadiddi.notesapp.dto.NoteReqDto;

import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/api")
public interface NoteApi {

    @PostMapping("/notes")
    public ResponseEntity<?> createNote(@RequestBody NoteReqDto req);

    @GetMapping("/notes")
    public ResponseEntity<?> getAllNotes();

}
