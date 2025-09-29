package ru.alishev.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alishev.springcourse.dao.HumanDAO;
import ru.alishev.springcourse.models.Human;

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
}
