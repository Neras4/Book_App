package com.neras4.book_app;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        if (books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(books);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable int id) {
        Optional<Book> matchingBook = books.stream()
                .filter(book -> book.getId() == id)
                .findFirst();

        if (matchingBook.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(matchingBook.get());
        } else {
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    "Not Found",
                    "Book with ID " + id + " not found."
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        if (book == null || book.getTitle() == null || book.getAuthor() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request",
                            "Invalid request. Book data must not be null."));
        }

        boolean bookExists = books.stream().anyMatch(existingBook -> existingBook.getId() == book.getId());
        if (bookExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new CustomErrorResponse(HttpStatus.CONFLICT.value(), "Conflict",
                            "Book with the same ID already exists."));
        }

        books.add(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        if (updatedBook == null || updatedBook.getTitle() == null || updatedBook.getAuthor() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request",
                            "Invalid request. Book data must not be null."));
        }

        Optional<Book> matchingBook = books.stream().filter(book -> book.getId() == id).findFirst();
        if (matchingBook.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found",
                            "Book with ID " + id + " not found."));
        }

        Book bookToUpdate = matchingBook.get();
        bookToUpdate.setTitle(updatedBook.getTitle());
        bookToUpdate.setAuthor(updatedBook.getAuthor());

        return ResponseEntity.status(HttpStatus.OK).body(bookToUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id) {
        Optional<Book> matchingBook = books.stream()
                .filter(book -> book.getId() == id)
                .findFirst();

        if (matchingBook.isEmpty()) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    "Not Found",
                    "Book with ID " + id + " not found."
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        books.remove(matchingBook.get());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}