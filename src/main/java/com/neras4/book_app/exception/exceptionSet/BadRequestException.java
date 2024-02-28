package com.neras4.book_app.exception.exceptionSet;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException() {
    }

    public String getMessage() {
        return "Bad Request";
    }
}
