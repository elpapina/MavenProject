package org.example.service;

public class ConsoleEmailService implements EmailService {
    @Override
    public void sendEmail(String email, String message) {
        System.out.println("Письмо для " + email + ": " + message);
    }
}