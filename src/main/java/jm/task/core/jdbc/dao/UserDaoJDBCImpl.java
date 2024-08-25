package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection;

    {
        try {
            connection = getConnection();
            System.out.println("Соединение установлено!");
        } catch (SQLException e) {
            System.out.println("Соединение не установлено!");
            throw new RuntimeException(e);
        }
    }

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {

        try (Statement statement = connection.createStatement()) {
            try {
                String SQL = """
                        CREATE TABLE `testbd`.`users` (
                          `id` BIGINT NOT NULL AUTO_INCREMENT,
                          `name` VARCHAR(45) NOT NULL,
                          `lastname` VARCHAR(45) NOT NULL,
                          `age` TINYINT NOT NULL,
                          PRIMARY KEY (`id`));""";
                statement.executeUpdate(SQL);
            } catch (SQLException e) {
                System.out.println("Таблица уже существует");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            try {
                String SQL = "DROP TABLE `testbd`.`users`;";
                statement.executeUpdate(SQL);
            } catch (SQLException e) {
                System.out.println("Не возможно удалить таблицу: таблицы не сущетвует");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = connection.createStatement()) {
            try {
            String SQL = "INSERT INTO users VALUES( id ,'" + name + "','" + lastName + "','" + age + "')";
            statement.executeUpdate(SQL);
            System.out.println("User с именем — " + name + " добавлен в базу данных");
            } catch (SQLException e) {
                System.out.println("User с именем — " + name + " не добавлен в базу данных");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Statement statement = connection.createStatement()) {
            try {
            String SQL = "DELETE fROM users where id";
            statement.executeUpdate(SQL);
            } catch (SQLException e) {
                System.out.println("User с id — " + id + " не удалён из базы данных");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String SQL = "SELECT * FROM users";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL);) {
            try {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
            } catch (SQLException e) {
                System.out.println("Ошибка получения списка User-s");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            try {
            String SQL = "DELETE fROM users";
            statement.executeUpdate(SQL);
            } catch (SQLException e) {
                System.out.println("Ошибка очистки таблицы");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
