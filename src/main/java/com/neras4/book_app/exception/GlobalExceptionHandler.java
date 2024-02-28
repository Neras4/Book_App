package com.neras4.book_app.exception;

import com.neras4.book_app.exception.exceptionSet.BadRequestException;
import com.neras4.book_app.exception.exceptionSet.ConflictException;
import com.neras4.book_app.exception.exceptionSet.NoContentException;
import com.neras4.book_app.exception.exceptionSet.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<String> badRequestException(BadRequestException ex) {
        CustomErrorResponse badRequestMessage = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), "Invalid request. Book data must not be null.");
        return new ResponseEntity<>(badRequestMessage.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ConflictException.class)
    public ResponseEntity<String> conflictException(ConflictException ex) {
        CustomErrorResponse conflictMessage = new CustomErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), "Book with the same ID already exists.");
        return new ResponseEntity<>(conflictMessage.toString(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = NoContentException.class)
    public ResponseEntity<String> noContentException(NoContentException ex) {
        CustomErrorResponse noContent = new CustomErrorResponse(HttpStatus.NO_CONTENT.value(), ex.getMessage(), "No books in a books shelf");
        return new ResponseEntity<>(noContent.toString(), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<String> notFoundException(NotFoundException ex) {
        CustomErrorResponse notFound = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), "Book with ID " + ex.getBookId() + " not found.");
        return new ResponseEntity<>(notFound.toString(), HttpStatus.NOT_FOUND);
    }
}
