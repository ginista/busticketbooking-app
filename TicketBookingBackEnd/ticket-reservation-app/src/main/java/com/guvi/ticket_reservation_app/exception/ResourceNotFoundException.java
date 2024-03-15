package com.guvi.ticket_reservation_app.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    private String errorMessage;

    public ResourceNotFoundException(String errorMessage){
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
