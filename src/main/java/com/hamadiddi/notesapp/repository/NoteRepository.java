package com.hamadiddi.notesapp.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hamadiddi.notesapp.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long>{

    Optional<Note> findByTitle(String title);

    Page<Note> findAllNotesByUserId(Long userId, Pageable pageable);

    Page<Note> searchNotesByUserId(Long userId, Pageable pageable, String search);

    Optional<Note> findByIdAndUserId(Long id, Long userId);

}
