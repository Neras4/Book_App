package com.neras4.book_app.exception.exceptionSet;

public class NotFoundException extends RuntimeException {
    private final int bookId;

    public NotFoundException(int bookId) {
        super("Not Found");
        this.bookId = bookId;
    }

    public int getBookId() {
        return bookId;
    }
}
