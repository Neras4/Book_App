package com.neras4.book_app.service;

import com.neras4.book_app.exception.exceptionSet.*;
import com.neras4.book_app.model.Book;
import com.neras4.book_app.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooks_Positive() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Book 1", "Author 1"));
        books.add(new Book(2, "Book 2", "Author 2"));
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllBooks_NoContentException() {
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(NoContentException.class, () -> bookService.getAllBooks());
    }

    @Test
    public void testGetBookById_Positive() throws NotFoundException {
        Book book = new Book(1, "Author 1", "Book 1");
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1);

        assertNotNull(result);
        assertEquals("Book 1", result.getTitle());
        assertEquals("Author 1", result.getAuthor());
    }

    @Test
    public void testGetBookById_NotFoundException() {
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.getBookById(1));
    }

    @Test
    public void testAddBook_Positive() {
        Book book = new Book(1, "Author 1", "Book 1");
        when(bookRepository.existsById(1)).thenReturn(false);
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.addBook(book);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Book 1", result.getTitle());
        assertEquals("Author 1", result.getAuthor());
    }

    @Test
    public void testAddBook_ConflictException() {
        Book book = new Book(1, "Book 1", "Author 1");
        when(bookRepository.existsById(1)).thenReturn(true);

        assertThrows(ConflictException.class, () -> bookService.addBook(book));
    }

    @Test
    public void testAddBook_BadRequestException() {
        Book invalidBook = new Book();

        assertThrows(BadRequestException.class, () -> bookService.addBook(invalidBook));
    }

    @Test
    public void testUpdateBook_Positive() {
        int id = 1;
        Book bookToUpdate = new Book(id, "Updated Author", "Updated Title");
        Book existingBook = new Book(id, "Original Author", "Original Title");
        when(bookRepository.findById(id)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Book result = bookService.updateBook(id, bookToUpdate);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Author", result.getAuthor());
    }

    @Test
    public void testUpdateBook_NotFoundException() {
        int id = 1;
        Book bookToUpdate = new Book(id, "Updated Title", "Updated Author");
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.updateBook(id, bookToUpdate));
    }

    @Test
    public void testUpdateBook_BadRequestException() {
        int id = 1;
        Book invalidBook = new Book();

        assertThrows(BadRequestException.class, () -> bookService.updateBook(id, invalidBook));
    }

    @Test
    public void testDeleteBook_Positive() {
        int id = 1;
        Book bookToDelete = new Book(id, "Author", "Title");
        when(bookRepository.findById(id)).thenReturn(Optional.of(bookToDelete));
        doNothing().when(bookRepository).delete(bookToDelete);

        Book result = bookService.deleteBook(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Title", result.getTitle());
        assertEquals("Author", result.getAuthor());
        verify(bookRepository, times(1)).findById(id);
        verify(bookRepository, times(1)).delete(bookToDelete);
    }

    @Test
    public void testDeleteBook_NotFoundException() {
        int id = 1;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.deleteBook(id));
    }
}