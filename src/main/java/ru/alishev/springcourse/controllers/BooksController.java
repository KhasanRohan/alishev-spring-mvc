package ru.alishev.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alishev.springcourse.dao.BookDAO;

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

}
