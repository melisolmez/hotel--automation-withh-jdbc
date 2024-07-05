package ui;

import model.Reservation;
import model.User;
import service.reservation.DefaultReservationService;
import service.reservation.ReservationWithCustomer;

import java.sql.SQLException;
import java.util.List;

public class ListAllCustomerMadeReservationsUI {
    private static final DefaultReservationService reservationService= new DefaultReservationService();
    public static void listCustomer() throws SQLException {
        List<ReservationWithCustomer> reservationsWithCustomers = reservationService.listMakeReservation();

        for (ReservationWithCustomer reservationWithCustomer : reservationsWithCustomers) {
            Reservation reservation = reservationWithCustomer.getReservation();
            User customer = reservationWithCustomer.getCustomer();

            System.out.println("Rezervasyon ID: " + reservation.getId());
            System.out.println("Oda Numarası: " + reservation.getRoomNumber());
            System.out.println("Başlangıç Tarihi: " + reservation.getBegDate());
            System.out.println("Bitiş Tarihi: " + reservation.getEndDate());
            System.out.println("Extra Hizmet: " + reservation.getExtraService());

            System.out.println("Müşteri ID: " + customer.getId());
            System.out.println("İsim: " + customer.getName());
            System.out.println("Soyisim: " + customer.getSurname());
            System.out.println("Tel: " + customer.getTel());
            System.out.println("----------------------------------------");
        }

    }
}
