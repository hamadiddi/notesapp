package com.hamadiddi.notesapp.dto;

import com.hamadiddi.notesapp.model.Note;

import lombok.Data;

@Data
public class NoteResDto {
    private Long id;
    private String title;
    private String content;
    private String username; // just the username of the user
    private String email; // and the email of course

    public NoteResDto(Note note) {
        this.id = note.getId();
        this.title = note.getTitle();
        this.content = note.getContent();
        this.username = note.getUser().getUsername(); // safe field only
        this.email = note.getUser().getEmail();
    }

    // getters and setters
}
