package com.neras4.book_app.service;

import com.neras4.book_app.exception.exceptionSet.BadRequestException;
import com.neras4.book_app.exception.exceptionSet.ConflictException;
import com.neras4.book_app.exception.exceptionSet.NoContentException;
import com.neras4.book_app.exception.exceptionSet.NotFoundException;
import com.neras4.book_app.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks() throws NoContentException;
    Book getBookById(int id) throws NotFoundException;
    Book addBook(Book book) throws BadRequestException, ConflictException;
    Book updateBook(int id, Book book) throws BadRequestException, NotFoundException;
    Book deleteBook(int id) throws NotFoundException;

}
