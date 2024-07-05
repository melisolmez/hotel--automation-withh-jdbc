package ui;

import model.Reservation;
import model.User;
import service.reservation.DefaultReservationService;
import service.reservation.ReservationWithCustomer;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ListCustomerReservationUI {

    private static final DefaultReservationService reservationService= new DefaultReservationService();

    public static void listCustomerReservation() throws SQLException {
        Scanner sc= new Scanner(System.in);
        String tc;

        System.out.println("Lütfen Tc kimlik numaranızı giriniz");
        tc=sc.nextLine();

        List<Reservation> reservations = reservationService.listReservationByUserTc(tc);

        for (Reservation reservation : reservations) {

            System.out.println("Rezervasyon ID: " + reservation.getId());
            System.out.println("Oda Numarası: " + reservation.getRoomNumber());
            System.out.println("Başlangıç Tarihi: " + reservation.getBegDate());
            System.out.println("Bitiş Tarihi: " + reservation.getEndDate());
            System.out.println("Extra Hizmet " + reservation.getExtraService());

            System.out.println("----------------------------------------");
        }
    }
}
