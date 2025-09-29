package ru.alishev.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alishev.springcourse.exception.PersonNotFoundException;
import ru.alishev.springcourse.models.Human;

import java.util.List;

@Component
public class HumanDAO {
    private final JdbcTemplate jdbcTemplate;
    private final static String SQL = "SELECT * FROM public.person";

    @Autowired
    public HumanDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Human> allHumans() {
        return jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Human.class));
    }

    public void save(Human human) {
        jdbcTemplate.update("INSERT INTO public.person (full_name, year_of_birth) values (?, ?)",
                human.getFullName(), human.getYearOfBirth());
    }

    public Human show(int personId) {
        return jdbcTemplate.query(SQL + " WHERE person_id = ?", new BeanPropertyRowMapper<>(Human.class), personId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new PersonNotFoundException("Person not found with id: " + personId));

    }
}
