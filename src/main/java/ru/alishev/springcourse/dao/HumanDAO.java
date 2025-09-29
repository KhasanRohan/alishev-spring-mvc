package ru.alishev.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
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
}
