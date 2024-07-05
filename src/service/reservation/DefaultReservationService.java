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
        PreparedStatement pstmt;
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "insert into reservation (roomid,customerid,begdate,enddate,extraservice) values (?,?,?,?,?) ";
            pstmt = connection.prepareStatement(sql);
            if(findRoomId(reservation.getRoomNumber())>0){
                pstmt.setInt(1, findRoomId(reservation.getRoomNumber()));

            }
            if(findUserId(reservation.getCustomerTc())>0){
                pstmt.setInt(2, findUserId(reservation.getCustomerTc()));
            }
            pstmt.setDate(3, reservation.getBegDate());
            pstmt.setDate(4, reservation.getEndDate());
            pstmt.setString(5, reservation.getExtraService());
            int row = pstmt.executeUpdate();
            if (row > 0) {
                System.out.println("Rezervasyon başarıyla yapıldı!");
            }

            String updateRoomSql = "UPDATE room SET isempty = false WHERE id = ?";
            try (PreparedStatement updatePstmt = connection.prepareStatement(updateRoomSql)) {
                updatePstmt.setInt(1,  findRoomId(reservation.getRoomNumber()));
                updatePstmt.executeUpdate();

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
    public List<ReservationWithCustomer> listMakeReservation() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        List<ReservationWithCustomer> reservationsWithCustomers = new ArrayList<>();
        String sql = "SELECT r.id AS reservationid, r.customerid, r.roomid, r.begdate, r.enddate, r.extraservice, "
                + "u.id AS userid, u.name, u.surname, u.tel, "
                + "ro.roomnumber "
                + "FROM reservation r "
                + "INNER JOIN user u ON r.customerid = u.id "
                + "INNER JOIN room ro ON r.roomid = ro.id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservationid");
                int customerId = resultSet.getInt("customerid");
                java.sql.Date begDate = resultSet.getDate("begdate");
                java.sql.Date endDate = resultSet.getDate("enddate");
                String extraService= resultSet.getString("extraservice");
                int roomNumber = resultSet.getInt("roomnumber");

                Reservation reservation = new Reservation();
                reservation.setId(reservationId);
                reservation.setCustomerTc(String.valueOf(customerId));
                reservation.setRoomNumber(roomNumber);
                reservation.setBegDate(begDate);
                reservation.setEndDate(endDate);
                reservation.setExtraService(extraService);

                int userId = resultSet.getInt("userid");
                String name = resultSet.getString("name");
                String surname= resultSet.getString("surname");
                String tel = resultSet.getString("tel");

                User customer = new User();
                customer.setId(userId);
                customer.setName(name);
                customer.setSurname(surname);
                customer.setTel(tel);

                reservationsWithCustomers.add(new ReservationWithCustomer(reservation, customer));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (connection != null) {
                connection.close();
            }
        }

        return reservationsWithCustomers;
    }

    @Override
    public void updateReservation(int roomNumber,Reservation reservation) {
        Connection connection = null;
        String selectSql = "SELECT begdate, enddate, extraservice FROM reservation WHERE roomid = ?";
        String updateSql = "UPDATE reservation SET begdate = ?, enddate = ?, extraservice = ? WHERE id = ?";

        try {
            connection = DatabaseConnection.getConnection();

            try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
                selectStatement.setInt(1, findRoomId(roomNumber));

                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Date begDate = resultSet.getDate("begdate");
                        Date endDate = resultSet.getDate("enddate");
                        String extraService = resultSet.getString("extraservice");

                        Date newBegDate = reservation.getBegDate() != null ? reservation.getBegDate() : begDate;
                        Date newEndDate = reservation.getEndDate() != null ? reservation.getEndDate() : endDate;
                        String newExtraService = reservation.getExtraService() != null ? reservation.getExtraService() : extraService;

                        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                            updateStatement.setDate(1, (java.sql.Date) newBegDate);
                            updateStatement.setDate(2, (java.sql.Date) newEndDate);
                            updateStatement.setString(3, newExtraService);
                            updateStatement.setInt(4, findRoomId(roomNumber));

                            int rowsAffected = updateStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Rezervasyon başarıyla güncellendi.");
                            } else {
                                System.out.println("Rezervasyon güncelleme başarısız.");
                            }
                        }
                    } else {
                        System.out.println("Rezervasyon bulunamadı.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<Reservation> listReservationByUserTc(String userTc) throws SQLException {
        int userId = findUserId(userTc);
        List<Reservation> reservations = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();

        String sql = "SELECT * FROM reservation WHERE customerid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int roomId = resultSet.getInt("roomid");
                    java.sql.Date begDate = resultSet.getDate("begdate");
                    java.sql.Date endDate = resultSet.getDate("enddate");
                    String extraService = resultSet.getString("extraservice");

                    Reservation reservation = new Reservation();
                    reservation.setId(id);
                    reservation.setRoomNumber(roomId);
                    reservation.setBegDate(begDate);
                    reservation.setEndDate(endDate);
                    reservation.setExtraService(extraService);

                    reservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                DatabaseConnection.closeConnection(connection);
            }
        }

        return reservations;

    }


    private int findRoomId(int roomNumber) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "SELECT id, isempty FROM room WHERE roomnumber = ?";
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
                return roomId;
        }
        return -1;
    }

    public static int findUserId(String Tc) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
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