package com.librarymanager.controller;

import com.librarymanager.model.Book;
import com.librarymanager.service.BookLoanService;
import com.librarymanager.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/librarian")
@Tag(name = "Librarian Management", description = "APIs for librarians to manage books and loan requests")
public class LibrarianController {

    private final BookService bookService;
    private final BookLoanService bookLoanService;

    public LibrarianController(BookService bookService, BookLoanService bookLoanService) {
        this.bookService = bookService;
        this.bookLoanService = bookLoanService;
    }

    @GetMapping("/books")
    @Operation(summary = "Get all books", description = "Retrieve a list of all books")
    public String listBooks(Model model) {
        List<Book> books = bookService.findAllBooks();
        model.addAttribute("books", books);
        return "librarian/books";
    }

    @GetMapping("/books/new")
    @Operation(summary = "Create new book", description = "Form to create a new book")
    public String createBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "librarian/create_book";
    }

    @PostMapping("/books")
    @Operation(summary = "Save new book", description = "Save a new book to the database")
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.saveBook(book);
        return "redirect:/librarian/books";
    }

    @GetMapping("/books/edit/{id}")
    @Operation(summary = "Edit book", description = "Form to edit an existing book")
    public String editBookForm(@PathVariable Long id, Model model) {
        Book book = bookService.findBookById(id);
        model.addAttribute("book", book);
        return "librarian/edit_book";
    }

    @PostMapping("/books/{id}")
    @Operation(summary = "Update book", description = "Update an existing book in the database")
    public String updateBook(@PathVariable Long id, @ModelAttribute("book") Book book) {
        bookService.updateBook(id, book);
        return "redirect:/librarian/books";
    }

    @GetMapping("/books/delete/{id}")
    @Operation(summary = "Delete book", description = "Delete an existing book from the database")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/librarian/books";
    }

    @GetMapping("/loans")
    @Operation(summary = "Get all loan requests", description = "Retrieve a list of all book loan requests")
    public String listLoanRequests(Model model) {
        List<Book> loanRequests = bookLoanService.findAllLoanRequests();
        model.addAttribute("loanRequests", loanRequests);
        return "librarian/loan_requests";
    }

    @PostMapping("/loans/approve/{id}")
    @Operation(summary = "Approve loan request", description = "Approve a book loan request")
    public String approveLoanRequest(@PathVariable Long id) {
        bookLoanService.approveLoanRequest(id);
        return "redirect:/librarian/loans";
    }

    @PostMapping("/loans/reject/{id}")
    @Operation(summary = "Reject loan request", description = "Reject a book loan request")
    public String rejectLoanRequest(@PathVariable Long id) {
        bookLoanService.rejectLoanRequest(id);
        return "redirect:/librarian/loans";
    }
}