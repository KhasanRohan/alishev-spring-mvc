package ru.alishev.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.dao.BookDAO;
import ru.alishev.springcourse.models.Book;

@Controller
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookDAO bookDAO;

    @GetMapping()
    public String allBooksPage(Model model) {
        model.addAttribute("books", bookDAO.allBooks());
        return "books/all_books";
    }

    @GetMapping("/{id}")
    public String showOneBook(@PathVariable("id") int bookId, Model model) {
        model.addAttribute("book", bookDAO.show(bookId));
        return "books/show_one_book";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/create_book";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") Book book) {
        bookDAO.save(book);
        return "redirect:/books";
    }


}
