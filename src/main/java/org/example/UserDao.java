package org.example;

import java.sql.*;

public class UserDao {
    private Connection getConnection() {
        String url = "jdbc:h2:mem://localhost/~/jdbc-practice;MODE=MySQL;DB_CLOSE_DELAY=-1";
        String id = "sa";
        String pw = "";

        try {
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection(url, id, pw);

        } catch (Exception e) {
            throw null;
        }
    }

    public void create(User user) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            con = getConnection();
            String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getEmail());

            preparedStatement.execute();
        }finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }


    public User findByUserId(String userId) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            con = getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, userId);

            resultSet = preparedStatement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                user = new User(
                        resultSet.getString("userId"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                );
            }

            return user;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
