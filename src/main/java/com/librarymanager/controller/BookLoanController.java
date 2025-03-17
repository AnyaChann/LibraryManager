package com.librarymanager.controller;

import com.librarymanager.model.Book;
import com.librarymanager.service.BookLoanService;
import com.librarymanager.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/loans")
public class BookLoanController {

    private final BookLoanService bookLoanService;
    private final BookService bookService;

    public BookLoanController(BookLoanService bookLoanService, BookService bookService) {
        this.bookLoanService = bookLoanService;
        this.bookService = bookService;
    }

    @GetMapping
    public String listLoans(Model model) {
        List<Book> overdueBooks = bookLoanService.findOverdueBooks();
        model.addAttribute("overdueBooks", overdueBooks);
        return "admin/loans";
    }
}