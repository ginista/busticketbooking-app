package com.guvi.ticket_reservation_app.service;

import com.guvi.ticket_reservation_app.entity.User;
import com.guvi.ticket_reservation_app.model.enums.Role;
import com.guvi.ticket_reservation_app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenUserFound() {
        // Arrange
        String username = "existingUser";
        User user = createUserWithRoles(username, Collections.singletonList(Role.ROLE_USER));

        when(userRepository.findByName(username)).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = authenticationService.loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails);
        assertEquals(user.getName(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().containsAll(user.getRolesList().stream()
                .map(Role::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList())));

        // Verify interactions
        verify(userRepository, times(1)).findByName(username);
    }

    @Test
    void loadUserByUsername_ShouldThrowUsernameNotFoundException_WhenUserNotFound() {
        // Arrange
        String username = "nonExistentUser";

        when(userRepository.findByName(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> authenticationService.loadUserByUsername(username));

        // Verify interactions
        verify(userRepository, times(1)).findByName(username);
    }

    // Utility methods...

    private User createUserWithRoles(String userName, List<Role> roles) {
        User user = new User();
        user.setName(userName);
        user.setEmail(userName + "@example.com");
        user.setPassword("password");
        user.setRolesList(roles);
        return user;
    }
}
