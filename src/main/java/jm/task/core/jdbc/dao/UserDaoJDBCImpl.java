package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;
import static jm.task.core.jdbc.util.Util.getNameOfDb;

public class UserDaoJDBCImpl implements UserDao {
    private static UserDaoJDBCImpl instance;
    private static Connection connection;
    private static final String NameOfDb = getNameOfDb();

    static {
        try {
            connection = getConnection();
            System.out.println("Соединение установлено!");
        } catch (SQLException e) {
            System.out.println("Ошибка установки соединения!");
            throw new RuntimeException(e);
        }
    }

    private UserDaoJDBCImpl() {
    }

    // Создание одного экземпляра
    public static UserDaoJDBCImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoJDBCImpl();
        }
        return instance;
    }

    // Закрытие соединения
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Соединение закрыто!");
            } else {
                System.out.println("Соединение уже было закрыто!");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при закрытии соединения!");
            throw new RuntimeException(e);
        }
    }

    // Открытие соединения, если потребуется
    public void openConnection() {
        try {
            if (connection == null || connection.isClosed()) {

                connection = getConnection();
                System.out.println("Соединение установлено!");

            } else {
                System.out.println("Соединение уже установленно!");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка установки соединения!");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createUsersTable() {

        try (Statement statement = connection.createStatement()) {
            String SQL =
                    "CREATE TABLE" + "'" + NameOfDb + "'" + ".`users` ("
                            + "`id`BIGINT NOT NULL AUTO_INCREMENT,"
                            + "`name`VARCHAR(45) NOT NULL,"
                            + "`lastname`VARCHAR(45) NOT NULL,"
                            + "`age`TINYINT NOT NULL,"
                            + "PRIMARY KEY(`id`))";
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            System.out.println("Возникли проблемы с получением данных из БД");
            throw new RuntimeException(e);
        }
    }


    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String SQL = "DROP TABLE" + "'" + NameOfDb + "'" + ".`users`;";
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            System.out.println("Не возможно удалить таблицу, возникли проблемы с получением данных из БД");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = connection.createStatement()) {
            String SQL = "INSERT INTO users VALUES( id ,'" + name + "','" + lastName + "','" + age + "')";
            statement.executeUpdate(SQL);
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Statement statement = connection.createStatement()) {
            String SQL = "DELETE fROM users where id";
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            System.out.println("User с id — " + id + " не был удалён из базы данных, возникли проблемы с получением данных из БД");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String SQL = "SELECT * FROM users";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL);) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения списка User-s, возникли проблемы с получением данных из БД");
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String SQL = "DELETE fROM users";
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            System.out.println("Ошибка очистки таблицы, возникли проблемы с получением данных из БД");
            throw new RuntimeException(e);
        }
    }
}
