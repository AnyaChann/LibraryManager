package com.librarymanager.service;

import com.librarymanager.model.Book;
import com.librarymanager.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookLoanService {

    private final BookRepository bookRepository;

    public BookLoanService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findOverdueBooks() {
        Date today = new Date();
        return bookRepository.findAll().stream()
                .filter(book -> !book.isAvailable() && book.getPublishedDate().before(today))
                .toList();
    }
}