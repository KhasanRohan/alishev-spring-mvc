package ru.alishev.springcourse.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.dao.BookDAO;
import ru.alishev.springcourse.dao.HumanDAO;
import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.models.Human;

@Controller
@RequestMapping("/books")
public class BooksController {

    private BookDAO bookDAO;

    private HumanDAO humanDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, HumanDAO humanDAO) {
        this.bookDAO = bookDAO;
        this.humanDAO = humanDAO;
    }

    @GetMapping()
    public String allBooksPage(Model model) {
        model.addAttribute("books", bookDAO.allBooks());
        return "books/all_books";
    }

    @GetMapping("/{id}")
    public String showOneBook(@PathVariable("id") int bookId, Model model) {
        model.addAttribute("book", bookDAO.show(bookId));
        model.addAttribute("isAvailable", bookDAO.isBookAvailable(bookId));
        model.addAttribute("personsBook", bookDAO.findPersonByBookId(bookId).orElse(null));
        model.addAttribute("people", humanDAO.allHumans());
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

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit_book";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteThatBook(@PathVariable("id") int id) {
        bookDAO.remove(id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int bookId,
                             @RequestParam("personId") int personId) {
        bookDAO.assignBook(personId, bookId);
        return "redirect:/books/{id}";
    }

    @PostMapping("/{id}/release")
    public String release(@PathVariable("id") int bookId) {
        bookDAO.releaseBook(bookId);
        return "redirect:/books/{id}";
    }

}
