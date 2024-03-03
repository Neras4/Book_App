package com.neras4.book_app.controller;

import com.neras4.book_app.exception.exceptionSet.BadRequestException;
import com.neras4.book_app.exception.exceptionSet.ConflictException;
import com.neras4.book_app.exception.exceptionSet.NoContentException;
import com.neras4.book_app.exception.exceptionSet.NotFoundException;
import com.neras4.book_app.model.Book;
import com.neras4.book_app.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks_Success() throws NoContentException {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Book 1", "Author 1"));
        books.add(new Book(2, "Book 2", "Author 2"));
        when(bookService.getAllBooks()).thenReturn(books);

        ResponseEntity<List<Book>> responseEntity = bookController.getAllBooks();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(books, responseEntity.getBody());
    }

    @Test
    void testGetAllBooks_NoContent() throws NoContentException {
        when(bookService.getAllBooks()).thenThrow(new NoContentException());

        assertThrows(NoContentException.class, () -> bookController.getAllBooks());
    }

    @Test
    void testGetBookById_Success() throws NotFoundException {
        int id = 1;
        Book book = new Book(id, "Book 1", "Author 1");
        when(bookService.getBookById(id)).thenReturn(book);

        ResponseEntity<Book> responseEntity = bookController.getBookById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(book, responseEntity.getBody());
    }

    @Test
    void testGetBookById_NotFound() throws NotFoundException {
        int id = 1;
        when(bookService.getBookById(id)).thenThrow(new NotFoundException(id));

        assertThrows(NotFoundException.class, () -> bookController.getBookById(id));
    }

    @Test
    void testAddBook_Success() throws BadRequestException, ConflictException {
        Book newBook = new Book(1, "New Book", "New Author");
        when(bookService.addBook(newBook)).thenReturn(newBook);

        ResponseEntity<Book> responseEntity = bookController.addBook(newBook);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(newBook, responseEntity.getBody());
    }

    @Test
    void testAddBook_BadRequest() throws BadRequestException, ConflictException {
        Book invalidBook = new Book();
        when(bookService.addBook(invalidBook)).thenThrow(new BadRequestException());

        assertThrows(BadRequestException.class, () -> bookController.addBook(invalidBook));
    }

    @Test
    void testAddBook_Conflict() throws BadRequestException, ConflictException {
        Book existingBook = new Book(1, "Existing Book", "Existing Author");
        when(bookService.addBook(existingBook)).thenThrow(new ConflictException());

        assertThrows(ConflictException.class, () -> bookController.addBook(existingBook));
    }

    @Test
    void testUpdateBook_Success() throws BadRequestException, NotFoundException {
        int id = 1;
        Book updatedBook = new Book(id, "Updated Book", "Updated Author");
        when(bookService.updateBook(id, updatedBook)).thenReturn(updatedBook);

        ResponseEntity<Book> responseEntity = bookController.updateBook(id, updatedBook);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedBook, responseEntity.getBody());
    }

    @Test
    void testUpdateBook_BadRequest() throws BadRequestException, NotFoundException {
        int id = 1;
        Book invalidBook = new Book();
        when(bookService.updateBook(id, invalidBook)).thenThrow(new BadRequestException());

        assertThrows(BadRequestException.class, () -> bookController.updateBook(id, invalidBook));
    }

    @Test
    void testUpdateBook_NotFound() throws BadRequestException, NotFoundException {
        int id = 1;
        Book bookToUpdate = new Book(id, "Updated Book", "Updated Author");
        when(bookService.updateBook(id, bookToUpdate)).thenThrow(new NotFoundException(id));

        assertThrows(NotFoundException.class, () -> bookController.updateBook(id, bookToUpdate));
    }

    @Test
    void testDeleteBook_Success() throws NotFoundException {
        int id = 1;
        Book deletedBook = new Book(id, "Deleted Book", "Deleted Author");
        when(bookService.deleteBook(id)).thenReturn(deletedBook);

        ResponseEntity<Book> responseEntity = bookController.deleteBook(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(deletedBook, responseEntity.getBody());
    }

    @Test
    void testDeleteBook_NotFound() throws NotFoundException {
        int id = 1;
        when(bookService.deleteBook(id)).thenThrow(new NotFoundException(id));

        assertThrows(NotFoundException.class, () -> bookController.deleteBook(id));
    }
}

