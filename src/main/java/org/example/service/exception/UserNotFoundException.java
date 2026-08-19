package org.example.service.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int id) {
        super("Не найден пользователь с id: " + id);
    }
}