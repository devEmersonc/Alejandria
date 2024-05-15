package com.library.backend.services.servicesImp;

import com.library.backend.entities.User;
import com.library.backend.repositories.RoleRepository;
import com.library.backend.repositories.UserRepository;
import com.library.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        User newUser = new User();

        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRoles(roleRepository.findByName("ROLE_USER"));

        return userRepository.save(newUser);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);

    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User update(User user, Long id) {
        User updatedUser = userRepository.findById(id).orElse(null);

        updatedUser.setUsername(user.getUsername());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(updatedUser);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
