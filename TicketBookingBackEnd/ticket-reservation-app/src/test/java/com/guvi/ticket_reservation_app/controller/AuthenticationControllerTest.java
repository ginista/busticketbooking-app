package com.guvi.ticket_reservation_app.controller;

import com.guvi.ticket_reservation_app.model.AuthenticationRequest;
import com.guvi.ticket_reservation_app.util.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void generateToken_ShouldReturnToken_WhenAuthenticationIsSuccessful() {
        // Arrange
        AuthenticationRequest authenticationRequest = createValidAuthenticationRequest();
        Authentication authentication = Mockito.mock(Authentication.class);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtUtils.generateToken(authenticationRequest.getUserName())).thenReturn("mockedToken");

        // Act
        ResponseEntity<String> responseEntity = authenticationController.generateToken(authenticationRequest);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("mockedToken", responseEntity.getBody());

        // Verify interactions
        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtUtils, times(1)).generateToken(authenticationRequest.getUserName());
    }

    @Test
    void generateToken_ShouldThrowUsernameNotFoundException_WhenAuthenticationFails() {
        // Arrange
        AuthenticationRequest authenticationRequest = createValidAuthenticationRequest();

        when(authenticationManager.authenticate(any())).thenThrow(new UsernameNotFoundException("Invalid user request"));

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> authenticationController.generateToken(authenticationRequest));

        // Verify interactions
        verify(authenticationManager, times(1)).authenticate(any());
        verifyNoInteractions(jwtUtils);
    }

    // Utility methods...

    private AuthenticationRequest createValidAuthenticationRequest() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUserName("user1");
        authenticationRequest.setPassword("password");
        return authenticationRequest;
    }

}
