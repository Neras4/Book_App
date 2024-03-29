package com.neras4.book_app.controller;

import com.neras4.book_app.exception.exceptionSet.BadRequestException;
import com.neras4.book_app.exception.exceptionSet.ConflictException;
import com.neras4.book_app.exception.exceptionSet.NoContentException;
import com.neras4.book_app.exception.exceptionSet.NotFoundException;
import com.neras4.book_app.logger.LoggerApp;
import com.neras4.book_app.model.Book;
import com.neras4.book_app.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    private final BookService bookService;
    private final LoggerApp loggerApp;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
        this.loggerApp = new LoggerApp(this.getClass().getName());

    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() throws NoContentException {
        loggerApp.logs();
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) throws NotFoundException{
        loggerApp.logs();
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @PostMapping("/book")
    public ResponseEntity<Book> addBook(@RequestBody Book book) throws BadRequestException,
            ConflictException {
        Book addBook = bookService.addBook(book);
        loggerApp.logs();
        return new ResponseEntity<>(addBook, HttpStatus.OK);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book)
            throws BadRequestException, NotFoundException {
        Book updateBook = bookService.updateBook(id, book);
        loggerApp.logs();
        return new ResponseEntity<>(updateBook, HttpStatus.OK);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) throws NotFoundException {
        Book deleteBook = bookService.deleteBook(id);
        loggerApp.logs();
        return  new ResponseEntity<>(deleteBook, HttpStatus.OK);
    }
}