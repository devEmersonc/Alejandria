package com.library.backend.controllers;

import com.library.backend.entities.User;
import com.library.backend.services.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUser/{user_id}")
    public ResponseEntity<?> getUser(@PathVariable Long user_id){
        User user = userService.findUserById(user_id);
        Map<String, Object> response = new HashMap<>();

        if(user == null){
            response.put("message", "El usuario no existe.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put("user", user);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){
        User newUser = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = new ArrayList<>();
            for(FieldError err: result.getFieldErrors()){
                errors.add(err.getDefaultMessage());
            }

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if(userService.existsByUsername(user.getUsername())){
            List<String> errors = new ArrayList<>();
            String errorUsername = "El nombre de usuario ya existe.";
            errors.add(errorUsername);

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try{
            newUser = userService.register(user);
        }catch (Exception e){
            response.put("message", "Error al realizar el registro.");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Se ha registrado al usuario exitosamente.");
        response.put("user", newUser);
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
    }

    @PutMapping("/update/{user_id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long user_id){
        User currentUser = userService.findUserById(user_id);
        User updatedUser = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = new ArrayList<>();
            for(FieldError err: result.getFieldErrors()){
                errors.add(err.getDefaultMessage());
            }

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if(currentUser == null){
            response.put("message", "El usuario no existe.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try{
            updatedUser = userService.update(user, user_id);
        }catch (Exception e){
            response.put("message", "Error al actualizar al usuario.");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Usuario actualizado.");
        response.put("user", updatedUser);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<?> delete(@PathVariable Long user_id){
        User user = userService.findUserById(user_id);
        Map<String, Object> response = new HashMap<>();

        if(user == null){
            response.put("message", "El usuario no existe.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        userService.deleteUserById(user.getId());
        response.put("message", "Usuario eliminado.");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
