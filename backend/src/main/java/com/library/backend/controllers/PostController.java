package com.library.backend.controllers;

import com.library.backend.entities.Post;
import com.library.backend.entities.User;
import com.library.backend.repositories.UserRepository;
import com.library.backend.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/post")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all-posts")
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }

    @PostMapping("/post/{user_id}")
    public ResponseEntity<?> newPost(@Valid @RequestBody Post post, BindingResult result, @PathVariable Long user_id){
        User user = userRepository.findById(user_id).orElse(null);
        Post newPost = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = new ArrayList<>();
            for(FieldError err: result.getFieldErrors()){
                errors.add(err.getDefaultMessage());
            }

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if(user == null){
            response.put("message", "El usuario ingresado no se ha encontrado.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try{
            newPost = postService.saveNewPost(post, user_id);
        }catch (Exception e){
            response.put("message", "Error al guardar el POST.");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "El POST se ha guardado con éxito.");
        response.put("post", newPost);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
