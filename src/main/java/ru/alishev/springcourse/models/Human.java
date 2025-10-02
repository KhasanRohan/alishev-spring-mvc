package ru.alishev.springcourse.models;

import java.util.ArrayList;
import java.util.List;

public class Human {
    private int personId;
    private String fullName;
    private int yearOfBirth;
//    private List<Book> bookList = new ArrayList<>();

    public Human() {
    }

    public Human(String fullName, int yearOfBirth) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
