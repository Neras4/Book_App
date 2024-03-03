package com.neras4.book_app.service;

import com.neras4.book_app.exception.exceptionSet.BadRequestException;
import com.neras4.book_app.exception.exceptionSet.ConflictException;
import com.neras4.book_app.exception.exceptionSet.NoContentException;
import com.neras4.book_app.exception.exceptionSet.NotFoundException;
import com.neras4.book_app.logger.LoggerApp;
import com.neras4.book_app.model.Book;
import com.neras4.book_app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final LoggerApp loggerApp;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.loggerApp = new LoggerApp(this.getClass().getName());
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = (List<Book>) bookRepository.findAll();
        if(books.isEmpty()) {
            loggerApp.logs();
            throw new NoContentException();
        }

        loggerApp.logs();
        return books;
    }

    @Override
    public Book getBookById(int id) throws NotFoundException {
        if (bookRepository.findById(id).isEmpty()) {
            loggerApp.logs();
            throw new NotFoundException(id);
        }

        loggerApp.logs();
        return bookRepository.findById(id).get();
    }

    @Override
    public Book addBook(Book book) {
        validateBook(book);

        if (bookRepository.existsById(book.getId())) {
            loggerApp.logs();
            throw new ConflictException();
        }

        loggerApp.logs();
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(int id, Book book) {
        validateBook(book);
        Book bookToUpdate = findBookByIdOrThrow(id);

        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());

        loggerApp.logs();
        return bookRepository.save(bookToUpdate);
    }

    @Override
    public Book deleteBook(int id) {
        Book removeBook = findBookByIdOrThrow(id);

        bookRepository.delete(removeBook);
        loggerApp.logs();
        return removeBook;
    }

    private void validateBook(Book book) {
        loggerApp.logs();
        if (book == null || book.getTitle() == null || book.getAuthor() == null) {
            loggerApp.logs();
            throw new BadRequestException();
        }
    }

    private Book findBookByIdOrThrow(int id) {
        loggerApp.logs();
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElseThrow(() -> new NotFoundException(id));
    }
}
