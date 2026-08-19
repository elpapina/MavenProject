package org.example;

import org.example.model.User;
import org.example.service.EmailService;
import org.example.service.exception.UserNotFoundException;
import org.example.repository.UserRepository;
import org.example.service.UserServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeAll
    static void init() {
        System.out.println("=== Запуск тестов UserService ===");
    }

    @Test
    @DisplayName("getUser возвращает пользователя если он существует")
    void getUser_existingId_returnsUser() {
        User user = new User(1, "Аня", "anya@mail.com");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUser(1);

        assertTrue(result.isPresent());
        assertEquals("Аня", result.get().getName());
    }

    @Test
    @DisplayName("getUser бросает исключение если пользователя не существует")
    void getUser_nonExistingId_throwsException() {
        when(userRepository.findById(-1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUser(-1));
    }

    @Test
    @DisplayName("createUser сохраняет пользователя и отправляет email")
    void createUser_validData_savesAndSendsEmail() {
        User saved = new User(1, "Аня", "anya@mail.com");
        when(userRepository.save(any(User.class))).thenReturn(saved);

        User result = userService.createUser("Аня", "anya@mail.com");

        assertEquals("Аня", result.getName());
        verify(emailService).sendEmail(eq("anya@mail.com"), anyString());
    }

    @Test
    @DisplayName("createUser передаёт в repository.save() пользователя с правильными полями")
    void createUser_validData_passesCorrectUserToRepository() {
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        userService.createUser("Борис", "boris@mail.com");

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());

        User captured = captor.getValue();
        assertEquals("Борис", captured.getName());
        assertEquals("boris@mail.com", captured.getEmail());
    }

    @Test
    @DisplayName("createUser бросает исключение если имя null")
    void createUser_nullName_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(null, "example@mail.com"));

        verify(userRepository, never()).save(any());
        verify(emailService, never()).sendEmail(any(), any());
    }

    @Test
    @DisplayName("deleteUser удаляет существующего пользователя")
    void deleteUser_existingId_callsDeleteById() {
        when(userRepository.existsById(1)).thenReturn(true);

        userService.deleteUser(1);

        verify(userRepository).deleteById(1);
    }

    @Test
    @DisplayName("deleteUser бросает исключение при попытке удалить несуществующего пользователя")
    void deleteUser_nonExistingId_throwsException() {
        when(userRepository.existsById(-1)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(-1));
    }

    @AfterAll
    static void tearDown() {
        System.out.println("=== Тесты завершены ===");
    }
}