package com.librarymanager.controller;

import com.librarymanager.model.Book;
import com.librarymanager.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
@Tag(name = "Book Management", description = "APIs for managing books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieve a list of all books")
    public String listBooks(Model model) {
        List<Book> books = bookService.findAllBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/new")
    @Operation(summary = "Create new book", description = "Form to create a new book")
    public String createBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "create_book";
    }

    @PostMapping
    @Operation(summary = "Save new book", description = "Save a new book to the database")
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    @Operation(summary = "Edit book", description = "Form to edit an existing book")
    public String editBookForm(@PathVariable Long id, Model model) {
        Book book = bookService.findBookById(id);
        model.addAttribute("book", book);
        return "edit_book";
    }

    @PostMapping("/{id}")
    @Operation(summary = "Update book", description = "Update an existing book in the database")
    public String updateBook(@PathVariable Long id, @ModelAttribute("book") Book book) {
        bookService.updateBook(id, book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    @Operation(summary = "Delete book", description = "Delete an existing book from the database")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}