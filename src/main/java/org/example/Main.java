package org.example;

import org.example.controller.CreateUserRequest;
import org.example.controller.UserController;
import org.example.controller.UserResponse;
import org.example.repository.InMemoryUserRepository;
import org.example.repository.UserRepository;
import org.example.service.ConsoleEmailService;
import org.example.service.EmailService;
import org.example.service.UserService;
import org.example.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserRepository repository = new InMemoryUserRepository();
        EmailService emailService = new ConsoleEmailService();
        UserService userService = new UserServiceImpl(repository, emailService);
        UserController controller = new UserController(userService);

        UserResponse created = controller.createUser(new CreateUserRequest("Аня", "anya@mail.com"));
        System.out.println(created);

        UserResponse found = controller.getUser(created.getId());
        System.out.println(found);

        controller.deleteUser(created.getId());
    }
}