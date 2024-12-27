package com.example.socialNetworking.service;

import com.example.socialNetworking.model.User;

public interface UserService {
    User getUserByEmail(String email);

    void saveUser(User user);
    
    User findById(Long userId);

    User updateUser(Long userId, User user);
}
