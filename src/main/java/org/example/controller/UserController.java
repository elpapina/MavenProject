package org.example.controller;

import org.example.model.User;
import org.example.service.UserService;

import java.util.Optional;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserResponse createUser(CreateUserRequest request) {
        User user = userService.createUser(request.getName(), request.getEmail());
        return toResponse(user);
    }

    public UserResponse getUser(int id) {
        Optional<User> user = userService.getUser(id);
        return toResponse(user.orElseThrow());
    }

    public void deleteUser(int id) {
        userService.deleteUser(id);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}