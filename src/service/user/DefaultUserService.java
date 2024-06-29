package service.user;


import databse.DatabaseConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class DefaultUserService implements UserService {
    @Override
    public void register(User user) throws SQLException {
        Connection connection=null;
        PreparedStatement pstmt=null;

        try{
            connection= DatabaseConnection.getConnection();
            String sql= "insert into user (name,surname,tc,tel) values (?,?,?,?) ";
            pstmt= connection.prepareStatement(sql);
            pstmt.setString(1,user.getName());
            pstmt.setString(2,user.getSurname());
            pstmt.setString(3,user.getTC());
            pstmt.setString(4,user.getTel());
            int row= pstmt.executeUpdate();

            if(row>0) {
                System.out.println("Kişi başarıyla kaydedildi");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if(pstmt!=null){
                pstmt.close();
            }
            if(connection!=null){
                DatabaseConnection.closeConnection(connection);
            }
        }
    }
}
