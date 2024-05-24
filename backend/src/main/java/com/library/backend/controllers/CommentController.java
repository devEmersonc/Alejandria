package com.library.backend.controllers;

import com.library.backend.entities.Comment;
import com.library.backend.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/new-comment/{user_id}/{post_id}")
    public ResponseEntity<?> addNewComment(@Valid @RequestBody Comment comment, BindingResult result, @PathVariable Long user_id, @PathVariable Long post_id){
        Comment newComment = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = new ArrayList<>();
            for(FieldError err: result.getFieldErrors()){
                errors.add(err.getDefaultMessage());
            }

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try{
            newComment = commentService.addNewComment(comment, user_id, post_id);
        }catch (Exception e){
            response.put("message", "Error al realizar el insert en la BD.");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Comentario guardado con éxito.");
        response.put("comment", newComment);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
