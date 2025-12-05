package com.hamadiddi.notesapp.controller.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.hamadiddi.notesapp.controller.api.NoteApi;
import com.hamadiddi.notesapp.dto.NoteReqDto;
import com.hamadiddi.notesapp.dto.NoteResDto;
import com.hamadiddi.notesapp.model.Note;
import com.hamadiddi.notesapp.model.Users;
import com.hamadiddi.notesapp.repository.NoteRepository;
import com.hamadiddi.notesapp.repository.UserRepository;
import com.hamadiddi.notesapp.utils.ErrorResponse;
import com.hamadiddi.notesapp.utils.SuccessResponse;

@RestController
public class NoteController implements NoteApi {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;  

    @Override
    public ResponseEntity<?> createNote(NoteReqDto req) {

    if (req.getTitle() == null || req.getTitle().trim().isEmpty()) {
        ErrorResponse errorResponse = new ErrorResponse("error", "Title cannot be empty");
        return ResponseEntity.status(400).body(errorResponse);
    }

    Optional<Note> nOptional = noteRepository.findByTitle(req.getTitle());
    if (nOptional.isPresent()) {
        ErrorResponse errorResponse = new ErrorResponse("error", "Title already exists");
        return ResponseEntity.status(409).body(errorResponse);
    }

    // Get the authenticated user per request
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    Users user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Authenticated user not found!"));

    Note note = new Note();
    note.setTitle(req.getTitle());
    note.setContent(req.getContent());
    note.setUser(user);

    Note saved = noteRepository.save(note);

    // Map to DTO
    NoteResDto resDto = new NoteResDto(saved);

    SuccessResponse<NoteResDto> successResponse =
        new SuccessResponse<>("success", "Note created successfully", resDto);

    return ResponseEntity.status(201).body(successResponse);

    
}


}
