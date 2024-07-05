package service.room;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import databse.DatabaseConnection;
import model.Features;
import model.Room;
import service.reservation.ReservationWithCustomer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultRoomService implements RoomService {

    @Override
    public void addRoom(Room room) throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();

            String checkSql = "SELECT COUNT(*) FROM room WHERE roomnumber = ?";
            pstmt = connection.prepareStatement(checkSql);
            pstmt.setInt(1, room.getRoomNumber());
            resultSet = pstmt.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                System.out.println("Bu oda numarası zaten mevcut!");
                return;
            }

            String sql = "INSERT INTO room (roomCapacity, features, extrafeature, roomnumber, isempty, price) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, room.getRoomCapacity());
            String features = String.join(",", convertFeaturesToStringSet(room.getFeature()));
            pstmt.setString(2, features);
            pstmt.setString(3, room.getExtraFeatures());
            pstmt.setInt(4, room.getRoomNumber());
            pstmt.setBoolean(5, true);
            pstmt.setDouble(6, room.getPrice());

            int row = pstmt.executeUpdate();
            if (row > 0) {
                System.out.println("Oda başarıyla eklendi!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (connection != null) {
                DatabaseConnection.closeConnection(connection);
            }
        }
    }

    @Override
    public List<Room> listSuitableRoom() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        List<Room> rooms = new ArrayList<>();
        String sql=" Select * from room where isempty=true";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int roomId=  resultSet.getInt("id");
                int roomCapacity= resultSet.getInt("roomCapacity");
                String features= resultSet.getString("features");
                String extraFeatures=resultSet.getString("extrafeature");
                int roomNumber= resultSet.getInt("roomnumber");
                double price= resultSet.getDouble("price");

                Room room= new Room();
                room.setId(roomId);
                room.setRoomCapacity(roomCapacity);
                room.setFeature(convertStringToFeatureSet(features));
                room.setExtraFeatures(extraFeatures);
                room.setRoomNumber(roomNumber);
                room.setPrice(price);

                rooms.add(room);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                DatabaseConnection.closeConnection(connection);
            }
        }
        return rooms;
    }

    private Set<String> convertFeaturesToStringSet(Set<Features> featureSet) {
        return featureSet.stream()
                .map(Features::name)
                .collect(Collectors.toSet());
    }
    private Set<Features> convertStringToFeatureSet(String features) {
        return Arrays.stream(features.split(","))
                .map(Features::valueOf)
                .collect(Collectors.toSet());
    }

}
