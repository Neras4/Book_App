package com.neras4.book_app.exception.exceptionSet;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }

    public ConflictException() {
        super("Conflict");
    }
}

