package com.guvi.ticket_reservation_app.controller;

import com.guvi.ticket_reservation_app.model.ChangePasswordRequest;
import com.guvi.ticket_reservation_app.model.CreateUserRequest;
import com.guvi.ticket_reservation_app.model.UpdateUserRequest;
import com.guvi.ticket_reservation_app.model.UserResponse;
import com.guvi.ticket_reservation_app.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void registerUser_ShouldReturnCreatedStatus_WhenRegistrationIsSuccessful() {
        // Arrange
        CreateUserRequest createUserRequest = createValidCreateUserRequest();

        // Act
        ResponseEntity<String> responseEntity = userController.registerUser(createUserRequest);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Verify interactions
        verify(userService, times(1)).registerUser(createUserRequest);
    }

    @Test
    void getUserById_ShouldReturnUserResponse() {
        // Arrange
        Authentication authentication = createMockAuthentication();
        UserResponse userResponse = createValidUserResponse();

        when(userService.getUserByName(authentication.getName())).thenReturn(userResponse);

        // Act
        ResponseEntity<UserResponse> responseEntity = userController.getUserById(authentication);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userResponse, responseEntity.getBody());

        // Verify interactions
        verify(userService, times(1)).getUserByName(authentication.getName());
    }

    @Test
    void updateUserProfile_ShouldReturnAcceptedStatus_WhenUpdateIsSuccessful() {
        // Arrange
        Authentication authentication = createMockAuthentication();
        UpdateUserRequest updateUserRequest = createValidUpdateUserRequest();

        // Act
        ResponseEntity<String> responseEntity = userController.updateUserProfile(authentication, updateUserRequest);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());

        // Verify interactions
        verify(userService, times(1)).updateUserRequest(authentication.getName(), updateUserRequest);
    }

    @Test
    void updateUserPassword_ShouldReturnAcceptedStatus_WhenUpdateIsSuccessful() {
        // Arrange
        Authentication authentication = createMockAuthentication();
        ChangePasswordRequest changePasswordRequest = createValidChangePasswordRequest();

        // Act
        ResponseEntity<String> responseEntity = userController.updateUserPassword(authentication, changePasswordRequest);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());

        // Verify interactions
        verify(userService, times(1)).updateUserPassword(authentication.getName(), changePasswordRequest);
    }

    // Utility methods...

    private CreateUserRequest createValidCreateUserRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        // Set properties as needed
        return createUserRequest;
    }

    private Authentication createMockAuthentication() {
        return mock(Authentication.class);
    }

    private UserResponse createValidUserResponse() {
        UserResponse userResponse = new UserResponse();
        // Set properties as needed
        return userResponse;
    }

    private UpdateUserRequest createValidUpdateUserRequest() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        // Set properties as needed
        return updateUserRequest;
    }

    private ChangePasswordRequest createValidChangePasswordRequest() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        // Set properties as needed
        return changePasswordRequest;
    }
}
