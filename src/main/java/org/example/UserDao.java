package org.example;

import java.sql.*;

public class UserDao {

    public void create(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        String sql = "INSERT INTO USERS VALUES (?,?,?,?)";
        jdbcTemplate.executeUpdate(user, sql, preparedStatement -> {
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getEmail());
        });
    }

    public User findByUserId(String userId) throws SQLException {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        return (User) jdbcTemplate.executeQuery(sql,
                preparedStatement -> {
                    preparedStatement.setString(1, userId);
                },
                resultSet -> {
                    return new User(
                            resultSet.getString("userId"),
                            resultSet.getString("password"),
                            resultSet.getString("name"),
                            resultSet.getString("email")
                    );
                });
    }
}
