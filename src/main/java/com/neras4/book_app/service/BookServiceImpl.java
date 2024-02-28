package com.neras4.book_app.service;

import com.neras4.book_app.exception.exceptionSet.BadRequestException;
import com.neras4.book_app.exception.exceptionSet.ConflictException;
import com.neras4.book_app.exception.exceptionSet.NoContentException;
import com.neras4.book_app.exception.exceptionSet.NotFoundException;
import com.neras4.book_app.model.Book;
import com.neras4.book_app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = (List<Book>) bookRepository.findAll();
        if(books.isEmpty()) {
            throw new NoContentException();
        }

        return books;
    }

    @Override
    public Book getBookById(int id) throws NotFoundException {
        if (bookRepository.findById(id).isEmpty()) {
            throw new NotFoundException(id);
        }

        return bookRepository.findById(id).get();
    }

    @Override
    public Book addBook(Book book) {
        validateBook(book);

        if (bookRepository.existsById(book.getId())) {
            throw new ConflictException();
        }

        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(int id, Book book) {
        validateBook(book);
        Book bookToUpdate = findBookByIdOrThrow(id);

        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());

        return bookRepository.save(bookToUpdate);
    }

    @Override
    public Book deleteBook(int id) {
        Book removeBook = findBookByIdOrThrow(id);

        bookRepository.delete(removeBook);
        return removeBook;
    }

    private void validateBook(Book book) {
        if (book == null || book.getTitle() == null || book.getAuthor() == null) {
            throw new BadRequestException();
        }
    }

    private Book findBookByIdOrThrow(int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElseThrow(() -> new NotFoundException(id));
    }
}
