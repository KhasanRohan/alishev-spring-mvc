package ru.alishev.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alishev.springcourse.exception.BookNotFoundException;
import ru.alishev.springcourse.models.Book;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_ALL_BOOKS = "SELECT * FROM public.book";

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> allBooks(){
        return jdbcTemplate.query(FIND_ALL_BOOKS, new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int bookId) {
        return jdbcTemplate.query(FIND_ALL_BOOKS + " WHERE book_id = ?", new BeanPropertyRowMapper<>(Book.class), bookId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Книга не найдена с таким id = " + bookId));
    }
}
