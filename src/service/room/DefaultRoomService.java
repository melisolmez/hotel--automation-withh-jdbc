package service.room;

import databse.DatabaseConnection;
import model.Features;
import model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class DefaultRoomService implements RoomService {

    @Override
    public void addRoom(Room room) throws SQLException {

        Connection connection=null;
        PreparedStatement pstmt=null;
        try{
            connection=DatabaseConnection.getConnection();
            String sql= "insert into room (roomCapacity,features,extrafeature,roomnumber) values (?,?,?,?,?) ";
            pstmt= connection.prepareStatement(sql);
            pstmt.setInt(1,room.getRoomCapacity());
            String features = String.join(",", convertFeaturesToStringSet(room.getFeature()));
            pstmt.setString(2, features);
            pstmt.setString(3, room.getExtraFeatures());
            pstmt.setInt(4,room.getRoomNumber());
            pstmt.setBoolean(5,true);
            int row= pstmt.executeUpdate();
            if(row>0) {
                System.out.println("Oda başarıyla eklendi!");
            }

        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(pstmt!=null){
                pstmt.close();
            }
            if(connection!=null){
               DatabaseConnection.closeConnection(connection);
            }
        }

    }

    private Set<String> convertFeaturesToStringSet(Set<Features> featureSet) {
        return featureSet.stream()
                .map(Features::name)
                .collect(Collectors.toSet());
    }

}
