package org.example.service;

import org.example.model.User;
import java.util.Optional;

public interface UserService {
    Optional<User> getUser(int id);
    User createUser(String name, String email);
    void deleteUser(int id);
}