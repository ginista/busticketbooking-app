package com.guvi.ticket_reservation_app.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtUtilsTest {

    @Mock
    private SecretKey secretKey;

    @InjectMocks
    private JwtUtils jwtUtils;

    @Test
    void generateToken_ShouldGenerateToken() {
        // Arrange
        String username = "testUser";

        // Act
        String token = jwtUtils.generateToken(username);

        // Assert
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void validateToken_ShouldReturnTrue_WhenTokenIsValid() {
        // Arrange
        String username = "testUser";
        String token = jwtUtils.generateToken(username);
        UserDetails userDetails = new User(username, "", new ArrayList<>());

        // Act
        boolean isValid = jwtUtils.validateToken(token, userDetails);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void validateToken_ShouldReturnFalse_WhenTokenIsInvalid() {
        // Arrange
        String username = "testUser";
        String validToken = jwtUtils.generateToken(username);
        UserDetails userDetails = new User("differentUser", "", new ArrayList<>());

        // Act
        boolean isValid = jwtUtils.validateToken(validToken, userDetails);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void extractUsername_ShouldReturnUsername_WhenTokenIsValid() {
        // Arrange
        String username = "testUser";
        String token = jwtUtils.generateToken(username);

        // Act
        String extractedUsername = jwtUtils.extractUsername(token);

        // Assert
        assertEquals(username, extractedUsername);
    }
}
