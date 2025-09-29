package ru.alishev.springcourse.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.alishev.springcourse.models.Human;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HumanMapper implements RowMapper<Human> {
    @Override
    public Human mapRow(ResultSet rs, int rowNum) throws SQLException {
        Human human = new Human();

        human.setPersonId(rs.getInt("person_id"));
        human.setFullName(rs.getString("full_name"));
        human.setYearOfBirth(rs.getInt("year_of_birth"));
        return human;
    }
}
