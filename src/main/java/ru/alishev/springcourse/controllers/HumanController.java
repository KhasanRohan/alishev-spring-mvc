package ru.alishev.springcourse.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.dao.HumanDAO;
import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.models.Human;
import ru.alishev.springcourse.models.Person;

import java.util.List;

@Controller
@RequestMapping("/human")
public class HumanController {
    private HumanDAO humanDAO;

    @Autowired
    public HumanController(HumanDAO humanDAO) {
        this.humanDAO = humanDAO;
    }

    @GetMapping()
    public String allPeoplePage(Model model) {
        model.addAttribute("human", humanDAO.allHumans());
        return "human/all_people";
    }

    @GetMapping("/new")
    public String newHuman(@ModelAttribute("human") Human human) {
        return "human/new_human";
    }
    @PostMapping()
    public String createHuman(@ModelAttribute("human") Human human) {
        humanDAO.save(human);
        return "redirect:/human";
    }

    @GetMapping("/{id}")
    public String showOneHuman(@PathVariable("id") int personId, Model model) {
        List<Book> books = humanDAO.showBooksForThatHuman(personId);
        model.addAttribute("human",humanDAO.show(personId));
        model.addAttribute("books", books);
        return "human/show_one_human";
    }

    @GetMapping("/{id}/edit")
    public String editPage(Model model, @PathVariable("id") int id) {
        model.addAttribute("human", humanDAO.show(id));
        return "human/edit_human";
    }

    @PatchMapping("/{id}")
    public String updateHuman(@ModelAttribute("human") Human human, @PathVariable("id") int id) {
        humanDAO.edit(id, human);
        return "redirect:/human";
    }

    @DeleteMapping("/{id}")
    public String deleteThatHuman(@PathVariable("id") int id) {
        humanDAO.remove(id);
        return "redirect:/human";
    }

}
