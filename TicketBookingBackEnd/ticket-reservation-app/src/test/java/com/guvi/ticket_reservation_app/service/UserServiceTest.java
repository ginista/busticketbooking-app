package com.guvi.ticket_reservation_app.service;

import com.guvi.ticket_reservation_app.entity.User;
import com.guvi.ticket_reservation_app.exception.ResourceNotFoundException;
import com.guvi.ticket_reservation_app.model.ChangePasswordRequest;
import com.guvi.ticket_reservation_app.model.CreateUserRequest;
import com.guvi.ticket_reservation_app.model.UpdateUserRequest;
import com.guvi.ticket_reservation_app.model.enums.Gender;
import com.guvi.ticket_reservation_app.model.enums.Role;
import com.guvi.ticket_reservation_app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUser_ShouldThrowRuntimeException_WhenPasswordEncoderFails() {
        // Arrange
        CreateUserRequest createUserRequest = createValidUserRequest();

        when(passwordEncoder.encode(createUserRequest.getPassword())).thenThrow(new RuntimeException("Password encoding failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.registerUser(createUserRequest));

        // Verify interactions
        verifyNoInteractions(userRepository);
    }

    @Test
    void getUserByName_ShouldThrowResourceNotFoundException_WhenUserNotFound() {
        // Arrange
        String userName = "nonExistentUser";

        when(userRepository.findByName(userName)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByName(userName));

        // Verify interactions
        verify(userRepository, times(1)).findByName(userName);
    }

    @Test
    void updateUserRequest_ShouldThrowResourceNotFoundException_WhenUserNotFound() {
        // Arrange
        String userName = "nonExistentUser";
        UpdateUserRequest updateUserRequest = createValidUpdateUserRequest();

        when(userRepository.findByName(userName)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.updateUserRequest(userName, updateUserRequest));

        // Verify interactions
        verify(userRepository, times(1)).findByName(userName);
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void updateUserPassword_ShouldThrowResourceNotFoundException_WhenUserNotFound() {
        // Arrange
        String userName = "nonExistentUser";
        ChangePasswordRequest changePasswordRequest = createValidChangePasswordRequest();

        when(userRepository.findByName(userName)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.updateUserPassword(userName, changePasswordRequest));

        // Verify interactions
        verify(userRepository, times(1)).findByName(userName);
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void updateUserPassword_ShouldThrowRuntimeException_WhenPasswordMismatch() {
        // Arrange
        String userName = "user1";
        ChangePasswordRequest changePasswordRequest = createValidChangePasswordRequest();
        User user = createUserFromChangePasswordRequest(userName, changePasswordRequest);

        when(userRepository.findByName(userName)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.updateUserPassword(userName, changePasswordRequest));

        // Verify interactions
        verify(userRepository, times(1)).findByName(userName);
        verify(passwordEncoder, times(1)).matches(changePasswordRequest.getOldPassword(), user.getPassword());
        verify(userRepository, times(0)).save(any());
    }

    // Utility methods...

    private CreateUserRequest createValidUserRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setName("user1");
        createUserRequest.setEmail("user1@example.com");
        createUserRequest.setPassword("password");
        createUserRequest.setRoles(Collections.singletonList(Role.ROLE_USER));
        return createUserRequest;
    }

    private UpdateUserRequest createValidUpdateUserRequest() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setName("updatedUser");
        updateUserRequest.setGender(Gender.Male);
        updateUserRequest.setDateOfBirth(LocalDate.of(1990, 1, 1));
        updateUserRequest.setEmail("updatedUser@example.com");
        updateUserRequest.setMobileNumber("1234567890");
        return updateUserRequest;
    }

    private ChangePasswordRequest createValidChangePasswordRequest() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("oldPassword");
        changePasswordRequest.setNewPassword("newPassword");
        return changePasswordRequest;
    }

    private User createUserFromChangePasswordRequest(String userName, ChangePasswordRequest changePasswordRequest) {
        User user = new User();
        user.setName(userName);
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getOldPassword()));
        return user;
    }
}
