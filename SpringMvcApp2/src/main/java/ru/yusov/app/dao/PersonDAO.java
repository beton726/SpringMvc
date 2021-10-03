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
        people.add(new Person(++PEOPLE_COUNT, "Daniel", 24, "daniel@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Timur", 52, "timur@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Alex", 18, "alex@mail.ru"));
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
    //
    public void update(int id, Person person) {
        Person personToBeUpdated = show(id);

        personToBeUpdated.setName(person.getName());
        personToBeUpdated.setAge(person.getAge());
        personToBeUpdated.setEmail(person.getEmail());
    }
    //
    public void delete(int id) {
        people.removeIf(person -> person.getId() == id);
    }

}