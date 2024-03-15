package com.guvi.ticket_reservation_app.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
    @NotBlank(message = "Old Password is required")
    @Size(min = 6, max = 20, message = "Old Password must be between 6 and 20 characters")
    private String oldPassword;

    @NotBlank(message = "New Password is required")
    @Size(min = 6, max = 20, message = "New Password must be between 6 and 20 characters")
    private String newPassword;
}
