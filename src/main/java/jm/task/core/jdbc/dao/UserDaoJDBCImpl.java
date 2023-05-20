package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private String sql = "SELECT name, lastName, age, id FROM users";

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/baza_kata", "root", "roote");
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("create table if not exists users" +
                    "(" +
                    "    name     varchar(100) null," +
                    "    lastName varchar(100) null," +
                    "    age      int          null," +
                    "    id       int auto_increment" +
                    "        primary key" +
                    ");");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void dropUsersTable() {

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/baza_kata", "root", "roote");
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DROP TABLE IF EXISTS users;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/baza_kata", "root", "roote");
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT users (name, lastName, age) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/baza_kata", "root", "roote");
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM users WHERE id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/baza_kata", "root", "roote");
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = null;
            List<User> userList = new ArrayList<>();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setAge(resultSet.getByte("age"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/baza_kata", "root", "roote");
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
