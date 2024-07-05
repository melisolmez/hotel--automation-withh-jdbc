package service.user;


import databse.DatabaseConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DefaultUserService implements UserService {
    @Override
    public void register(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();

            String checkSql = "SELECT COUNT(*) FROM user WHERE tc = ?";
            pstmt = connection.prepareStatement(checkSql);
            pstmt.setString(1, user.getTC());
            resultSet = pstmt.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                System.out.println("Bu TC numarası ile kayıt zaten mevcut!");
                return;
            }

            String sql = "INSERT INTO user (name, surname, tc, tel) VALUES (?, ?, ?, ?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getSurname());
            pstmt.setString(3, user.getTC());
            pstmt.setString(4, user.getTel());
            int row = pstmt.executeUpdate();

            if (row > 0) {
                System.out.println("Kişi başarıyla kaydedildi");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (connection != null) {
                DatabaseConnection.closeConnection(connection);
            }
        }
    }
}

