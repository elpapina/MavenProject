package org.example.service;

import org.example.model.User;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    @Override
    public Optional<User> getUser(int id) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @Override
    public User createUser(String name, String email) {
        validateNotNull(name, email);
        User savedUser = repository.save(User.builder()
                .name(name)
                .email(email)
                .build());
        emailService.sendEmail(email, "Сообщение");
        return savedUser;
    }

    @Override
    public void deleteUser(int id) {
        if (!repository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        repository.deleteById(id);
    }

    private void validateNotNull(String name, String email) {
        if (name == null || email == null) {
            throw new IllegalArgumentException("Name и email не должны быть null");
        }
    }
}
