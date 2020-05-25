package com.blesk.messagingservice.Exception;

import org.springframework.http.HttpStatus;

public class MessagingServiceException extends RuntimeException {

    private HttpStatus httpStatus;

    public MessagingServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}