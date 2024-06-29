package service.reservation;

import databse.DatabaseConnection;
import model.Reservation;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefaultReservationService implements ReservationService {

    @Override
    public void makeReservation(Reservation reservation) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "insert into room (roomid,customerid,begdate,enddate,price) values (?,?,?,?,?,?) ";
            pstmt = connection.prepareStatement(sql);
            if(findRoomId(reservation.getRoomId())>0){
                pstmt.setInt(1, findRoomId(reservation.getRoomId()));
            }
            if(findUserId(reservation.getCustomerId())>0){
                pstmt.setInt(2, findUserId(reservation.getCustomerId()));
            }
            pstmt.setDate(3, reservation.getBegDate());
            pstmt.setDate(4, reservation.getEndDate());
            pstmt.setDouble(5, reservation.getPrice());
            int row = pstmt.executeUpdate();
            if (row > 0) {
                System.out.println("Rezervasyon başarıyla yapıldı!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                DatabaseConnection.closeConnection(connection);
            }
        }
    }

    @Override
    public List<ReservationWithCustomer> listMakeReservation() {
        Connection connection = null;
        List<ReservationWithCustomer> reservationsWithCustomers = new ArrayList<>();
        String sql = "SELECT r.reservationid, r.customerid, r.roomid, r.begdate, r.enddate, r.price, "
                + "u.userid, u.name, u.email "
                + "FROM reservation r "
                + "INNER JOIN user u ON r.customerid = u.userid";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservationid");
                int customerId = resultSet.getInt("customerid");
                int roomId = resultSet.getInt("roomid");
                Date begDate = resultSet.getDate("begdate");
                Date endDate = resultSet.getDate("enddate");
                double price = resultSet.getDouble("price");

                Reservation reservation = new Reservation(reservationId, customerId, roomId, begDate, endDate, price);
                reservation.setId(reservationId);
                reservation.setCustomerId(customerId);
                reservation.setRoomId(roomId);
                reservation.setBegDate(begDate);
                reservation.setEndDate(endDate);
                reservation.setPrice(price);

                int userId = resultSet.getInt("userid");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");

                User customer = new User(userId, name, email);

                reservationsWithCustomers.add(new ReservationWithCustomer(reservation, customer));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservationsWithCustomers;
    }

    private int findRoomId(int roomNumber){
        Connection connection = null;
        String sql = "SELECT id, isempty FROM room WHERE room_number = ?";
        int roomId = -1;
        boolean isEmpty = false;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, roomNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    roomId = resultSet.getInt("id");
                    isEmpty = resultSet.getBoolean("isempty");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (roomId != -1) {
            if (isEmpty) {
                return roomId;
            } else {
                return -1;
            }
        }
        return -1;
    }

    private int findUserId(String Tc){
        Connection connection = null;
        String sql = "SELECT id FROM user WHERE tc = ?";
        int userId = -1;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, Tc);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    userId = resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(userId!=-1){
            return userId;
        }
        return -1;

    }
}