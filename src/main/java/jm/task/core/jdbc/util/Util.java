package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost/baza_kata";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "roote";
    // реализуйте настройку соеденения с БД
    static Connection getConnection(){
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
        return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
