package com.neras4.book_app.exception.exceptionSet;

public class NotFoundException extends RuntimeException {
    private int bookId;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(int bookId) {
        super("Not Found");
        this.bookId = bookId;
    }

    public int getBookId() {
        return bookId;
    }
}
