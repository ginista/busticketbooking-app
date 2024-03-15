package com.guvi.ticket_reservation_app.controller;

import com.guvi.ticket_reservation_app.model.ChangePasswordRequest;
import com.guvi.ticket_reservation_app.model.CreateUserRequest;
import com.guvi.ticket_reservation_app.model.UpdateUserRequest;
import com.guvi.ticket_reservation_app.model.UserResponse;
import com.guvi.ticket_reservation_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        userService.registerUser(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserResponse> getUserById(Authentication authentication) {
        UserResponse userResponse = userService.getUserByName(authentication.getName());

        return ResponseEntity.ok(userResponse);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> updateUserProfile(Authentication authentication, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        userService.updateUserRequest(authentication.getName(), updateUserRequest);

        return ResponseEntity.accepted().build();
    }

    @PutMapping("/changePassword")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> updateUserPassword(Authentication authentication, @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.updateUserPassword(authentication.getName(), changePasswordRequest);

        return ResponseEntity.accepted().build();
    }
}
