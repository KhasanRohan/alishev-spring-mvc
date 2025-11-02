//package ru.alishev.springcourse.dao;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//import ru.alishev.springcourse.exception.PersonNotFoundException;
//import ru.alishev.springcourse.models.Book;
//import ru.alishev.springcourse.models.Human;
//
//import java.util.List;
//
//@Component
//public class HumanDAO {
//    private final JdbcTemplate jdbcTemplate;
//    private final static String FIND_ALL_HUMAN = "SELECT * FROM public.person";
//
//    @Autowired
//    public HumanDAO(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<Human> allHumans() {
//        return jdbcTemplate.query(FIND_ALL_HUMAN, new BeanPropertyRowMapper<>(Human.class));
//    }
//
//    public void save(Human human) {
//        jdbcTemplate.update("INSERT INTO public.person (full_name, year_of_birth) values (?, ?)",
//                human.getFullName(), human.getYearOfBirth());
//    }
//
//    public Human show(int personId) {
//        return jdbcTemplate.query(FIND_ALL_HUMAN + " WHERE person_id = ?", new BeanPropertyRowMapper<>(Human.class), personId)
//                .stream()
//                .findFirst()
//                .orElseThrow(() -> new PersonNotFoundException("Person not found with id: " + personId));
//
//    }
//
//    public void edit(int id, Human human) {
//        jdbcTemplate.update("UPDATE public.person SET full_name = ?, year_of_birth = ? WHERE person_id = ?",
//                human.getFullName(), human.getYearOfBirth(), id);
//    }
//
//    public List<Book> showBooksForThatHuman(int id) {
//        return jdbcTemplate.query("SELECT b.book_id, b.person_id, b.name, b.author, b.year FROM person p JOIN book b ON p.person_id = b.person_id WHERE p.person_id = ?",
//                new BeanPropertyRowMapper<>(Book.class), id);
//    }
//
//    public void remove(int id) {
//        jdbcTemplate.update("DELETE FROM public.person WHERE person_id = ?", id);
//    }
//}
