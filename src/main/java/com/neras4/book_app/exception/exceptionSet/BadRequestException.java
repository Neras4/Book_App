package com.neras4.book_app.exception.exceptionSet;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super("Bad Request");
    }
}
