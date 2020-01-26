package com.gmail.pashasimonpure.repository.impl;

import com.gmail.pashasimonpure.repository.UserRepository;
import com.gmail.pashasimonpure.repository.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private static final String DATABASE_NAME = "jd2_homework7_8";

    private static UserRepository instance;

    private UserRepositoryImpl() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void delete(Connection connection, Integer id) throws SQLException{

        String sql = "DELETE FROM user WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("UserRepository: delete user failed, no rows affected.");
            }

        }
    }

    @Override
    public User add(Connection connection, User user) throws SQLException {

        String sql = "INSERT INTO user(username, password, is_active, age) VALUES (?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setBoolean(3,user.getActive());
            statement.setInt(4,user.getAge());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("UserRepository: add user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("UserRepository: add user failed, no ID obtained.");
                }
            }

            return user;
        }

    }

    @Override
    public List<User> findAll(Connection connection) throws SQLException{

        String sql = "SELECT id, username, password, is_active, age FROM user";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            List<User> users = new ArrayList<>();

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {

                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setActive(rs.getBoolean("is_active"));
                    user.setAge(rs.getInt("age"));

                    users.add(user);

                }

                return users;
            }
        }

    }

    @Override
    public void dropTable(Connection connection) throws SQLException {

        String sql = "DROP TABLE IF EXISTS user";

        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();

    }

    @Override
    public void createTable(Connection connection) throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS user (" +
                "id INT(11) AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(40) NOT NULL," +
                "password VARCHAR(40) NOT NULL, " +
                "is_active TINYINT(1) DEFAULT 0, " +
                "age INT(11))";

        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();

    }

}