package com.sabbir.easynote.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.sabbir.easynote.exception.ResourceNotFoundException;
import com.sabbir.easynote.model.Note;
import com.sabbir.easynote.repository.NoteRepository;

@RestController
@RequestMapping("/api")
public class NoteController {
	
	@Autowired
	NoteRepository noteRepository;
	
	//Get all notes
	@GetMapping("notes")
	public List<Note> getAllNotes(){
		return noteRepository.findAll();
	}
	
	//create a new note
	@PostMapping("/notes")
	public Note createNote(@Valid @RequestBody Note note) {
		return noteRepository.save(note);
	}
	
	//get a single note
	@GetMapping("/notes/{id}")
	public Note getNoteById(@PathVariable (value = "id") Long noteId) {
		return noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
	}
	
	//update a note
	@PutMapping ("/notes/{id}")
	public Note updateNote(@PathVariable(value="id")Long noteId,@Valid @RequestBody Note noteDetails) {
		Note note = noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
		
		note.setTitle(noteDetails.getTitle());
		note.setContent(noteDetails.getContent());
		
		Note updateNote = noteRepository.save(note);
		return updateNote;
	}
	
	// Delete a Note
	@DeleteMapping("/notes/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
	    Note note = noteRepository.findById(noteId)
	            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

	    noteRepository.delete(note);

	    return ResponseEntity.ok().build();
	}

}
