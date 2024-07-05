package service.invoices;

import databse.DatabaseConnection;
import model.Invoices;
import service.reservation.DefaultReservationService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static service.reservation.DefaultReservationService.findUserId;

public class DefaultInvoiceService implements InvoicesService {
    @Override
    public void calculateInvoice(String userTc) {
        Connection connection = null;
        String selectReservationSql = "SELECT id, roomid, begdate, enddate, extraservice FROM reservation WHERE customerid = ?";
        String selectRoomSql = "SELECT price FROM room WHERE id = ?";
        String insertInvoiceSql = "INSERT INTO invoices (reservationid, invoicedate, totalamount) VALUES (?, ?, ?)";
        double totalAmount = 0.0;

        try {
            connection = DatabaseConnection.getConnection();

            try (PreparedStatement selectReservationStatement = connection.prepareStatement(selectReservationSql)) {
                selectReservationStatement.setInt(1, findUserId(userTc));

                try (ResultSet resultSet = selectReservationStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int reservationId = resultSet.getInt("id");
                        int roomId = resultSet.getInt("roomid");
                        Date begDate = resultSet.getDate("begdate");
                        Date endDate = resultSet.getDate("enddate");
                        String extraService = resultSet.getString("extraservice");

                        try (PreparedStatement selectRoomStatement = connection.prepareStatement(selectRoomSql)) {
                            selectRoomStatement.setInt(1, roomId);

                            try (ResultSet roomResultSet = selectRoomStatement.executeQuery()) {
                                if (roomResultSet.next()) {
                                    double pricePerDay = roomResultSet.getDouble("price");

                                    long diffInMillies = Math.abs(endDate.getTime() - begDate.getTime());
                                    long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);


                                    totalAmount = diffInDays * pricePerDay;
                                    if (extraService != null && !extraService.isEmpty()) {
                                        totalAmount += 100;
                                    }

                                    try (PreparedStatement insertInvoiceStatement = connection.prepareStatement(insertInvoiceSql)) {
                                        insertInvoiceStatement.setInt(1, reservationId);
                                        insertInvoiceStatement.setDate(2, new java.sql.Date(System.currentTimeMillis())); // Güncel tarih
                                        insertInvoiceStatement.setDouble(3, totalAmount);

                                        int rowsInserted = insertInvoiceStatement.executeUpdate();
                                        if (rowsInserted > 0) {
                                            System.out.println("Fatura başarıyla kaydedildi.");
                                        } else {
                                            System.out.println("Fatura kaydedilemedi.");
                                        }
                                    }
                                }
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
    public List<Invoices> listInvoice(String userTc) {
        calculateInvoice(userTc);
        Connection connection = null;
        List<Invoices> invoicesList = new ArrayList<>();
        String selectReservationSql = "SELECT id FROM reservation WHERE customerid = ?";
        String selectInvoiceSql = "SELECT * FROM invoices WHERE reservationid = ?";

        try {
            connection = DatabaseConnection.getConnection();

            try (PreparedStatement selectReservationStatement = connection.prepareStatement(selectReservationSql)) {
                selectReservationStatement.setInt(1, findUserId(userTc));
                try (ResultSet reservationResultSet = selectReservationStatement.executeQuery()) {
                    while (reservationResultSet.next()) {
                        int reservationId = reservationResultSet.getInt("id");

                        try (PreparedStatement selectInvoiceStatement = connection.prepareStatement(selectInvoiceSql)) {
                            selectInvoiceStatement.setInt(1, reservationId);
                            try (ResultSet invoiceResultSet = selectInvoiceStatement.executeQuery()) {
                                while (invoiceResultSet.next()) {
                                    Invoices invoice = new Invoices();
                                    invoice.setId(invoiceResultSet.getInt("invoiceid"));
                                    invoice.setReservationId(invoiceResultSet.getInt("reservationid"));
                                    invoice.setInvoiceDate(invoiceResultSet.getDate("invoicedate"));
                                    invoice.setTotalAmount(invoiceResultSet.getDouble("totalamount"));

                                    invoicesList.add(invoice);
                                }
                            }
                        }
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

        return invoicesList;
    }
}
