package ru.alishev.springcourse.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Human owner;

    @Column(name = "name")
    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 2, max = 100, message = "Название книги должно быть от 2 и 100 символов длинной")
    private String name;

    @Column(name = "author")
    @NotEmpty(message = "Автор книги не должен быть пустым")
    @Size(min = 2, max = 100, message = "Имя автора книги должно быть от 2 и 100 символов длинной")
    private String author;

    @Column(name = "year")
    @Min(value = 1500, message = "Год должен быть больше, чем 1500")
    private int year;

    @Column(name = "book_was_taken")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime bookWasTaken;

    @Transient
    private boolean expired;

    public Book() {
    }

    public Book(String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Human getOwner() {
        return owner;
    }

    public void setOwner(Human owner) {
        this.owner = owner;
    }

    public LocalDateTime getBookWasTaken() {
        return bookWasTaken;
    }

    public void setBookWasTaken(LocalDateTime bookWasTaken) {
        this.bookWasTaken = bookWasTaken;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean calculateExpired() {
        if (this.bookWasTaken == null) {
            return false;
        }
        LocalDateTime takenDate = this.bookWasTaken;
        LocalDateTime currentDate = LocalDateTime.now();
        long daysSinceTaken = ChronoUnit.DAYS.between(takenDate, currentDate);
        return daysSinceTaken > 10;
    }
}
