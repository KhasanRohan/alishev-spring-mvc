package ru.alishev.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.models.Human;

@Repository
public interface HumanRepository extends JpaRepository<Human, Integer> {

}
