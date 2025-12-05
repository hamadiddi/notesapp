package com.hamadiddi.notesapp.controller.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Note Management", description = "Endpoints for note management")
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

    @Override
    public ResponseEntity<?> getAllNotes(String search, int page, int size, String sortBy) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users user = userRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("User not found"));

        if (page < 0) page = 0;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        Page<Note> notePage;

        // Handle search safely
        if (search == null || search.trim().isEmpty() || search.equals("0")) {
            notePage = noteRepository.findAllNotesByUserId(user.getId(), pageable);
        } else {
            notePage = noteRepository.searchNotesByUserId(user.getId(), pageable, search.toUpperCase());
        }

       // If user requests a page number greater than total pages, reset to last available page
        if (page > notePage.getTotalPages() - 1 && notePage.getTotalPages() > 0) {
            pageable = PageRequest.of(notePage.getTotalPages() - 1, size, Sort.by(sortBy).descending());
        
            if (search == null || search.trim().isEmpty() || search.equals("0")) {
                notePage = noteRepository.findAllNotesByUserId(user.getId(), pageable);
            } else {
                notePage = noteRepository.searchNotesByUserId(user.getId(), pageable, search.toUpperCase());
            }
        }
        return ResponseEntity.ok(Map.of(
            "notes", notePage.getContent(),
            "currentPage", notePage.getNumber(),
            "totalItems", notePage.getTotalElements(),
            "totalPages", notePage.getTotalPages()
        ));

    }

    @Override
    public ResponseEntity<?> getNote(Long id) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isEmpty()) {
            ErrorResponse response = new ErrorResponse("fail", "Note with id " + id + " doesn't exist");
            return ResponseEntity.status(400).body(response);
        }
        return ResponseEntity.status(200).body(noteRepository.findById(id));
    }

    @Override
    public ResponseEntity<?> deleteNote(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users user = userRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("User not found"));

        // Check if note exists and belongs to user
        Note note = noteRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(()->new RuntimeException("Note not found or you do not have permission"));
        
        noteRepository.delete(note);

        SuccessResponse<String> success = new SuccessResponse<>(
            "success",
            "Note deleted successfully",
            "deleted"
        );
        return ResponseEntity.status(200).body(success);
    }

    


    


}
