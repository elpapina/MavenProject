package org.example.service;

import org.example.model.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(int id);
    User save(User user);
    void deleteById(int id);
    boolean existsById(int id);
}