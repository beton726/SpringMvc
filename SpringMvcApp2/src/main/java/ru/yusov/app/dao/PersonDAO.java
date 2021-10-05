package ru.yusov.app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yusov.app.models.Person;

import java.util.List;

/*
 * Взаимодействие с БД.
 * JDBC API - низкоуровневый.
 * JdbsTemplate - обёртка вокруг JDBC API. Часть Spring Framework.
 * Hibernate.
 * */
@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Возвращает всех пользователей.
    public List<Person> index() {
        return jdbcTemplate.query("select * from person;", new BeanPropertyRowMapper<>(Person.class));
    }

    // Возвращает человека по id, если такого нету вернёт null. (Возвращается список)
    public Person show(int id) {
        return jdbcTemplate.query("select * from person where id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    // Принимиает объект Person и добавляет в БД.
    public void save(Person person) {
        jdbcTemplate.update("insert into person values(1, ?, ?, ?)",
                person.getName(),
                person.getAge(),
                person.getEmail()
        );
    }

    // Обновление всех персон по заданному id.
    public void update(int id, Person person) {
        jdbcTemplate.update("update person set name = ?, age = ?, email = ? where id = ?",
                person.getName(),
                person.getAge(),
                person.getEmail(),
                id
        );
    }

    // Удаление всех персон по заданному id.
    public void delete(int id) {
        jdbcTemplate.update("delete from person where id = ?",
                id
        );
    }

}