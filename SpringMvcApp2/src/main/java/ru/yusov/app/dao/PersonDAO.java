package ru.yusov.app.dao;

import org.springframework.stereotype.Component;
import ru.yusov.app.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Взаимодействие с БД.
 * JDBC API - низкоуровневый.
 * JdbsTemplate - обёртка вокруг JDBC API. Часть Spring Framework.
 * Hibernate.
 * */
@Component
public class PersonDAO {

    private static int PEOPLE_COUNT = 0;

    private static final String URL = "jdbc:postgresql://localhost:5432/people";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1234qwer";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
    // Возвращает всех пользователей.
    public List<Person> index() {
        List<Person> people = new ArrayList<>();

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from person;");

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                person.setAge(resultSet.getInt("age"));

                people.add(person);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return people;
    }
    // Возвращает человека по id, если такого нету вернёт null.
    public Person show(int id) {
        Person person = null;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from person where id = ?");

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            // Сдвигаем на один раз.
            resultSet.next();

            person = new Person();

            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return person;
    }
    // Принимиает объект Person и добавляет в БД.
    public void save(Person person) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into person values(1, ?, ?, ?)");

            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
    // Обновление всех персон по заданному id.
    public void update(int id, Person person) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("update person set name = ?, age = ?, email = ? where id = ?");

            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
    // Удаление всех персон по заданному id.
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("delete from person where id = ?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

}