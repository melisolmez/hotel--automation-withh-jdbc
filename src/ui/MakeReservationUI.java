package ui;

import model.Reservation;
import model.Room;
import model.User;
import service.reservation.DefaultReservationService;
import service.reservation.ReservationWithCustomer;
import service.room.DefaultRoomService;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class MakeReservationUI {

    private static final DefaultReservationService reservationService= new DefaultReservationService();
    public static void makeReservation() throws SQLException {
        Scanner sc = new Scanner(System.in);
        int roomNumber;
        String tc;
        java.util.Date begDateUtil;
        java.util.Date endDateUtil;
        String extraService;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("*** Rezervasyon yapılabilcek odalar *** ");
        listRooms();
        System.out.println("Lütfen size uygun odaya rezervasyon yaptırmak için aşağıdaki bilgileri giriniz.");
        System.out.println("Rezervasyon yaptırmak istediğiniz odanın numarası:");
        roomNumber = sc.nextInt();
        sc.nextLine();
        System.out.println("TC kimlik numaranız:");
        tc = sc.nextLine();
        System.out.println("Otele Giriş Tarihiniz (yyyy-MM-dd formatında giriniz):");
        String begDateStr = sc.nextLine();
        System.out.println("Otele Çıkış Tarihiniz (yyyy-MM-dd formatında giriniz):");
        String endDateStr = sc.nextLine();
        System.out.println("Ekstra hizmetler (opsiyonel):");
        extraService = sc.nextLine();

        try {
            begDateUtil = dateFormat.parse(begDateStr);
            endDateUtil = dateFormat.parse(endDateStr);

            // Convert java.util.Date to java.sql.Date
            java.sql.Date begDateSql = new java.sql.Date(begDateUtil.getTime());
            java.sql.Date endDateSql = new java.sql.Date(endDateUtil.getTime());

            Reservation reservation = new Reservation();
            reservation.setRoomNumber(roomNumber);
            reservation.setCustomerTc(tc);
            reservation.setBegDate(begDateSql);
            reservation.setEndDate(endDateSql);
            reservation.setExtraService(extraService);

            DefaultReservationService reservationService = new DefaultReservationService();
            reservationService.makeReservation(reservation);

        } catch (ParseException e) {
            System.out.println("Tarih formatı hatalı. Lütfen yyyy-MM-dd formatını kullanın.");
            e.printStackTrace();
        }

    }

    private static void listRooms() throws SQLException {
        DefaultRoomService roomService= new DefaultRoomService();
        List<Room> rooms = roomService.listSuitableRoom();

        for (Room room : rooms) {

            System.out.println("Oda ID: " + room.getId());
            System.out.println("Oda Kapasitesi: " + room.getRoomCapacity());
            System.out.println("Özellikler: " + room.getFeature());
            System.out.println("Ekstra Özellikler: " + room.getExtraFeatures());
            System.out.println("Oda Numarası: " + room.getRoomNumber());
            System.out.println("Günlük Fiyat: " + room.getPrice());

            System.out.println("----------------------------------------");
        }
    }
}
