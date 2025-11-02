package ru.alishev.springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.models.Human;
import ru.alishev.springcourse.repositories.HumanRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class HumanService {

    private final HumanRepository humanRepository;

    @Autowired
    public HumanService(HumanRepository humanRepository) {
        this.humanRepository = humanRepository;
    }

    public List<Human> findAll() {
        return humanRepository.findAll();
    }

    public Human findOne(int id) {
        Optional<Human> foundHuman = humanRepository.findById(id);
        return foundHuman.orElse(null);
    }

    @Transactional
    public void save(Human human) {
        humanRepository.save(human);
    }

    @Transactional
    public void update(int id, Human human) {
        human.setPersonId(id);
        humanRepository.save(human);
    }

    @Transactional
    public void delete(int id) {
        humanRepository.deleteById(id);
    }
}
