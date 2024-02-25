package com.neras4.book_app.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Value(value = "${data.exception.badRequestMessage}")
    private String badRequestMessage;
    @Value(value = "${data.exception.conflictMessage}")
    private String conflictMessage;
    @Value(value = "${data.exception.noContent}")
    private String noContent;
    @Value(value = "${data.exception.notFound}")
    private String notFound;

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<String> badRequestException(BadRequestException badRequestException) {
        return new ResponseEntity<>(badRequestMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ConflictException.class)
    public ResponseEntity<String> conflictException(ConflictException conflictException) {
        return new ResponseEntity<>(conflictMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = NoContentException.class)
    public ResponseEntity<String> noContentException(NoContentException noContentException) {
        return new ResponseEntity<>(noContent, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<String> notFoundException(NotFoundException notFoundException) {
        return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
    }
}
