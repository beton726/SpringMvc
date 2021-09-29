package ru.yusov.app.dao;

import org.springframework.stereotype.Component;
import ru.yusov.app.models.Person;

import java.util.ArrayList;
import java.util.List;

/*
 * Взаимодействие с БД.
 *
 * */
@Component
public class PersonDAO {

    private static int PEOPLE_COUNT = 0;
    private List<Person> people;

    {
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT, "Daniel"));
        people.add(new Person(++PEOPLE_COUNT, "Timur"));
        people.add(new Person(++PEOPLE_COUNT, "Alex"));
    }

    public List<Person> index() {
        return people;
    }
    // Возвращает человека по id, если такого нету вернёт null.
    public Person show(int id) {
        return people.stream().filter(person->person.getId()==id).findAny().orElse(null);
    }
    // Принимиает объект Person и добавляет в динамический список
    public void save(Person person) {
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

}