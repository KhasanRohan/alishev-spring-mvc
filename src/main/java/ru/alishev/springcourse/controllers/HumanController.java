package ru.alishev.springcourse.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.models.Human;
import ru.alishev.springcourse.models.Person;
import ru.alishev.springcourse.services.BookService;
import ru.alishev.springcourse.services.HumanService;

import java.util.List;

@Controller
@RequestMapping("/human")
public class HumanController {

    private final HumanService humanService;
    private final BookService bookService;

    @Autowired
    public HumanController(HumanService humanService, BookService bookService) {
        this.humanService = humanService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String allPeoplePage(Model model) {
        model.addAttribute("human", humanService.findAll());
        return "human/all_people";
    }

    @GetMapping("/new")
    public String newHuman(@ModelAttribute("human") Human human) {
        return "human/new_human";
    }

    @PostMapping()
    public String createHuman(@ModelAttribute("human") Human human) {
        humanService.save(human);
        return "redirect:/human";
    }

    @GetMapping("/{id}")
    public String showOneHuman(@PathVariable("id") Integer personId, Model model) {
        List<Book> books = bookService.findBooksThatPerson(personId);
        model.addAttribute("human", humanService.findOne(personId));
        model.addAttribute("books", books);
        return "human/show_one_human";
    }

    @GetMapping("/{id}/edit")
    public String editPage(Model model, @PathVariable("id") int id) {
        model.addAttribute("human", humanService.findOne(id));
        return "human/edit_human";
    }

    @PatchMapping("/{id}")
    public String updateHuman(@ModelAttribute("human") Human human, @PathVariable("id") int id) {
        humanService.update(id, human);
        return "redirect:/human";
    }

    @DeleteMapping("/{id}")
    public String deleteThatHuman(@PathVariable("id") int id) {
        humanService.delete(id);
        return "redirect:/human";
    }

}
