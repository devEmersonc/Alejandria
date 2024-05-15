package com.library.backend.services;

import com.library.backend.models.AuthResponse;
import com.library.backend.models.AuthenticationRequest;

public interface AuthService {

    AuthResponse login(AuthenticationRequest request);

    boolean authenticateUser(String username, String password);
}
