package com.guvi.ticket_reservation_app.service;

import com.guvi.ticket_reservation_app.entity.User;
import com.guvi.ticket_reservation_app.exception.ResourceNotFoundException;
import com.guvi.ticket_reservation_app.model.*;
import com.guvi.ticket_reservation_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setName(createUserRequest.getName());
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        user.setRolesList(createUserRequest.getRoles());
        userRepository.save(user);
    }

    public UserResponse getUserByName(String name) {
        User user = userRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setName(user.getName());
        userResponse.setRoles(user.getRolesList());
        userResponse.setGender(user.getGender());
        userResponse.setDateOfBirth(user.getDateOfBirth());
        userResponse.setEmail(user.getEmail());
        userResponse.setMobileNumber(user.getMobileNumber());

        return userResponse;

    }

    public void updateUserRequest(String userName, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findByName(userName).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        user.setName(updateUserRequest.getName());
        user.setGender(updateUserRequest.getGender());
        user.setDateOfBirth(updateUserRequest.getDateOfBirth());
        user.setEmail(updateUserRequest.getEmail());
        user.setMobileNumber(updateUserRequest.getMobileNumber());

        userRepository.save(user);
    }

    public void updateUserPassword(String userName, ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findByName(userName).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        String encodedNewPassword = passwordEncoder.encode(changePasswordRequest.getNewPassword());

        if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            user.setPassword(encodedNewPassword);
        } else{
            throw new RuntimeException("Password Mismatch");
        }

        userRepository.save(user);
    }
}
