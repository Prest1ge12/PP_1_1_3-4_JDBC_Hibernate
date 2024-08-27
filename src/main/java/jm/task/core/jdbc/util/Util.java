package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

    private static final String bdSrc = "jdbc:mysql://localhost:3306/testbd";
    private static final String user = "Mrangel";
    private static final String password = "root";
    private static final String NameOfDb = "testbd";

    private Util() {
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection(bdSrc, user, password);
        return connection;
    }

    public static String getNameOfDb() {
        return NameOfDb;
    }
}

