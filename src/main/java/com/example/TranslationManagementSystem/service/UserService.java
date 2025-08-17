package com.example.TranslationManagementSystem.service;

import com.example.TranslationManagementSystem.entity.UserEntity;

public interface UserService {
    void saveUser(UserEntity user);
    String authenticate(String username, String password);
}