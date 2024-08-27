package jm.task.core.jdbc.service;


import jm.task.core.jdbc.model.User;

import java.util.List;

import static jm.task.core.jdbc.dao.UserDaoJDBCImpl.getInstance;


public class UserServiceImpl implements UserService {

    @Override
    public void createUsersTable() {
        getInstance().createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        getInstance().dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        getInstance().saveUser(name, lastName, age);
    }

    @Override
    public void removeUserById(long id) {
        getInstance().removeUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return getInstance().getAllUsers();
    }

    @Override
    public void cleanUsersTable() {
        getInstance().cleanUsersTable();
    }
}
