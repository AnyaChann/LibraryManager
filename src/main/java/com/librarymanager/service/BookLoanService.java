package com.librarymanager.service;

import com.librarymanager.model.Book;
import com.librarymanager.model.User;
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
                .filter(book -> !book.isAvailable() && book.getDueDate().before(today))
                .toList();
    }

    public void borrowBook(Long bookId, User user) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + bookId));
        book.setAvailable(false);
        // Set the due date to 14 days from today
        book.setDueDate(new Date(System.currentTimeMillis() + 14L * 24 * 60 * 60 * 1000));
        bookRepository.save(book);
        // Implement logic to associate the book with the user and set the due date
    }

    public List<Book> findLoanedBooksByUser(User user) {
        // Implement logic to find all books loaned by the user
        return bookRepository.findAll(); // Placeholder
    }

    public void returnBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + bookId));
        book.setAvailable(true);
        book.setDueDate(null); // Clear the due date
        bookRepository.save(book);
        // Implement logic to disassociate the book from the user
    }

    public List<Book> findAllLoanRequests() {
        // Implement logic to find all loan requests
        return bookRepository.findAll(); // Placeholder
    }

    public void approveLoanRequest(Long id) {
        // Implement logic to approve loan request
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        book.setAvailable(false);
        bookRepository.save(book);
    }

    public void rejectLoanRequest(Long id) {
        // Implement logic to reject loan request
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        book.setAvailable(true);
        bookRepository.save(book);
    }
}