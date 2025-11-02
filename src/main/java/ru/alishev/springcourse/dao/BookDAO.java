//package ru.alishev.springcourse.dao;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//import ru.alishev.springcourse.exception.BookNotFoundException;
//import ru.alishev.springcourse.exception.PersonNotFoundException;
//import ru.alishev.springcourse.mapper.BookMapper;
//import ru.alishev.springcourse.mapper.HumanMapper;
//import ru.alishev.springcourse.models.Book;
//import ru.alishev.springcourse.models.Human;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class BookDAO {
//    private final JdbcTemplate jdbcTemplate;
//    private static final String FIND_ALL_BOOKS = "SELECT * FROM public.book";
//    private static final String FIND_PERSON_BY_BOOK_ID = """
//            SELECT p.person_id, p.full_name, p.year_of_birth
//            FROM book b LEFT JOIN person p
//            ON p.person_id = b.person_id
//            WHERE b.book_id = ?
//            """;
//
//    @Autowired
//    public BookDAO(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<Book> allBooks() {
//        return jdbcTemplate.query(FIND_ALL_BOOKS, new BookMapper());
//    }
//
//    public Book show(int bookId) {
//        return jdbcTemplate.query(FIND_ALL_BOOKS + " WHERE book_id = ?", new BookMapper(), bookId)
//                .stream()
//                .findFirst()
//                .orElseThrow(() -> new BookNotFoundException("Книга не найдена с таким id = " + bookId));
//    }
//
//    public void save(Book book) {
//        jdbcTemplate.update("INSERT INTO public.book (name, author, year) VALUES (?, ?, ?)",
//                book.getName(), book.getAuthor(), book.getYear());
//    }
//
//    public void update(int id, Book book) {
//        jdbcTemplate.update("UPDATE public.book SET name = ?, author = ?, year = ? WHERE book_id = ?",
//                book.getName(), book.getAuthor(), book.getYear(), id);
//    }
//
//    public void remove(int id) {
//        jdbcTemplate.update("DELETE FROM public.book WHERE book_id = ?", id);
//    }
//
//    public boolean isBookAvailable(int id) {
//        return jdbcTemplate.query(FIND_ALL_BOOKS + " WHERE book_id = ? AND person_id IS NULL", new BookMapper(), id)
//                .stream()
//                .findFirst()
//                .isPresent();
//    }
//
//    public Optional<Human> findPersonByBookId(int id) {
//        return jdbcTemplate.query(FIND_PERSON_BY_BOOK_ID, new HumanMapper(), id)
//                .stream()
//                .findFirst();
//    }
//
//    public void assignBook(int personId, int bookId) {
//        jdbcTemplate.update("UPDATE public.book SET person_id = ? WHERE book_id = ?",
//                personId, bookId);
//    }
//
//    public void releaseBook(int bookId) {
//        jdbcTemplate.update("UPDATE public.book SET person_id = null WHERE book_id = ?", bookId);
//    }
//
//}
