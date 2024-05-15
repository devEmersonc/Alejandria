package com.library.backend.services.servicesImp;

import com.library.backend.entities.User;
import com.library.backend.models.AuthResponse;
import com.library.backend.models.AuthenticationRequest;
import com.library.backend.repositories.UserRepository;
import com.library.backend.security.JwtService;
import com.library.backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Override
    public AuthResponse login(AuthenticationRequest request) {
        AuthResponse token = new AuthResponse();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername());
        String jwt = jwtService.generateToken(user);
        token.setToken(jwt);
        return token;
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);

        if(user == null){
            return false;
        }else{
            if(BCrypt.checkpw(password, user.getPassword())){
                return true;
            }
            return false;
        }
    }
}
