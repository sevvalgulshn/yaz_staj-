package com.demo.authdemo.service;

import com.demo.authdemo.entity.User;
import com.demo.authdemo.repository.UserRepository;
import com.demo.authdemo.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean validateUser(LoginRequest request) {
        Optional<User> optUser = userRepository.findByUsername(request.getUsername());
        if (optUser.isPresent()) {
            User user = optUser.get();
            return user.getPassword().equals(request.getPassword()); // Şifre kontrolü
        }
        return false;
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}