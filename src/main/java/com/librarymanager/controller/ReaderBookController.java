package com.librarymanager.controller;

import com.librarymanager.model.Book;
import com.librarymanager.model.User;
import com.librarymanager.service.BookLoanService;
import com.librarymanager.service.BookService;
import com.librarymanager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reader/books")
@Tag(name = "Library Member Book Management", description = "APIs for library members to manage books")
public class ReaderBookController {

    private final BookService bookService;
    private final BookLoanService bookLoanService;
    private final UserService userService;

    public ReaderBookController(BookService bookService, BookLoanService bookLoanService, UserService userService) {
        this.bookService = bookService;
        this.bookLoanService = bookLoanService;
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Browse available books", description = "Retrieve a list of available books")
    public String listAvailableBooks(Model model) {
        List<Book> books = bookService.findAllBooks();
        model.addAttribute("books", books);
        return "reader/books";
    }

    @PostMapping("/borrow/{id}")
    @Operation(summary = "Request to borrow book", description = "Request to borrow a book")
    public String borrowBook(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        bookLoanService.borrowBook(id, user);
        return "redirect:/reader/books";
    }

    @GetMapping("/loans")
    @Operation(summary = "View loan history", description = "View loan history and due dates")
    public String viewLoanHistory(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<Book> loanedBooks = bookLoanService.findLoanedBooksByUser(user);
        model.addAttribute("loanedBooks", loanedBooks);
        return "reader/loan_history";
    }

    @PostMapping("/return/{id}")
    @Operation(summary = "Return book", description = "Return a borrowed book before the due date")
    public String returnBook(@PathVariable Long id) {
        bookLoanService.returnBook(id);
        return "redirect:/reader/books/loans";
    }
}