package ru.alishev.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.services.BookService;
import ru.alishev.springcourse.services.HumanService;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final HumanService humanService;

    @Autowired
    public BooksController(BookService bookService, HumanService humanService) {
        this.bookService = bookService;
        this.humanService = humanService;
    }

    //?page=1&books_per_page=3
    @GetMapping()
    public String allBooksPage(
            Model model, @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "books_per_page", required = false) Integer booksPerPage,
            @RequestParam(name = "sort_by_year", required = false) boolean sortByYear
    ) {
        if (page != null && booksPerPage != null) {
            model.addAttribute("books", bookService.pagination(page, booksPerPage, sortByYear));
        } else {
            model.addAttribute("books", bookService.findAll());
        }
        return "books/all_books";
    }

    @GetMapping("/{id}")
    public String showOneBook(@PathVariable("id") int bookId, Model model) {
        model.addAttribute("book", bookService.findOne(bookId));
        model.addAttribute("isAvailable", bookService.isBookAvailable(bookId));
        model.addAttribute("personsBook", bookService.findOne(bookId).getOwner());
        model.addAttribute("people", humanService.findAll());
        return "books/show_one_book";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/create_book";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") Book book) {
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit_book";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteThatBook(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int bookId,
                             @RequestParam("personId") int personId) {
        bookService.assignBook(personId, bookId);
        return "redirect:/books/" + bookId;
    }

    @PostMapping("/{id}/release")
    public String release(@PathVariable("id") int bookId) {
        bookService.releaseBook(bookId);
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "books/search_book";
    }

    @GetMapping("/find")
    public String searchBook(@RequestParam(name = "search", required = false) String searchQuery, RedirectAttributes redirectAttributes) {
        Book book = bookService.searchByNameBook(searchQuery);
        if (book.getBookId() != 0) {
            redirectAttributes.addFlashAttribute("book", book);
            redirectAttributes.addFlashAttribute("isAvailable", bookService.isBookAvailable(book.getBookId()));
            redirectAttributes.addFlashAttribute("personsBook", bookService.findOne(book.getBookId()).getOwner());
        }
        else {
            redirectAttributes.addFlashAttribute("error", "Книг не найдено");
        }
        return "redirect:/books/search";
    }

}
