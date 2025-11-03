package ru.alishev.springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.exception.BookNotFoundException;
import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.models.Human;
import ru.alishev.springcourse.repositories.BookRepository;
import ru.alishev.springcourse.repositories.HumanRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final HumanRepository humanRepository;

    @Autowired
    public BookService(BookRepository bookRepository, HumanRepository humanRepository) {
        this.bookRepository = bookRepository;
        this.humanRepository = humanRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findOne(int bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Книга не найдена с таким id = " + bookId));
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int bookId, Book book) {
        Book bookToBeUpdated = bookRepository.findById(bookId).get();
        book.setBookId(bookId);
        book.setOwner(bookToBeUpdated.getOwner());
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int bookId) {
        bookRepository.deleteById(bookId);
    }

    public boolean isBookAvailable(Integer id) {
        return bookRepository.findByOwnerIsNullAndBookId(id)
                .stream()
                .findFirst()
                .isPresent();
    }

    @Transactional
    public void assignBook(int personId, int bookId) {
        Human human = humanRepository.findById(personId).orElse(null);
        Book freeBook = bookRepository.findById(bookId).orElse(null);
        human.setBooks(new ArrayList<>(Collections.singletonList(freeBook)));
        freeBook.setOwner(human);
        bookRepository.save(freeBook);
    }

    @Transactional
    public void releaseBook(int bookId) {
        bookRepository.findById(bookId).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setBookWasTaken(null);
                }
        );

    }

    public List<Book> pagination(int page, int booksPerPage, boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        }
        return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public Book searchByNameBook(String searchQuery) {
        Book book = bookRepository.findByNameStartingWithIgnoreCase(searchQuery);
        if (book != null) {
            return book;
        } else {
            return new Book();
        }
    }

    public List<Book> findBooksWithExpiryInfo(Integer personId) {
        List<Book> bookList = bookRepository.findByOwnerPersonId(personId);
        bookList.forEach(book -> book.setExpired(book.calculateExpired()));
        return bookList;
    }
}
