package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {

    public void executeUpdate(User user, String sql, PreparedStatementSetter preparedStatementSetter) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            con = ConnectionManager.getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatementSetter.setter(preparedStatement);

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

    public Object executeQuery(String sql, PreparedStatementSetter preparedStatementSetter, RowMapper rowMapper) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            con = ConnectionManager.getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatementSetter.setter(preparedStatement);

            resultSet = preparedStatement.executeQuery();

            Object obj = null;
            if (resultSet.next()) {
                return rowMapper.map(resultSet);
            }

            return obj;
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
