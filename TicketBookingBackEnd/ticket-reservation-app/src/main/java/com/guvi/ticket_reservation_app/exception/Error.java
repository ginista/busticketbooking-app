package com.guvi.ticket_reservation_app.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Error {
    private int code;
    private List<String> messages;
    private LocalDateTime timestamp;
}
