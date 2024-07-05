package ui;

import model.Reservation;
import service.reservation.DefaultReservationService;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class UpdateReservationUI {
    private static final DefaultReservationService reservationService= new DefaultReservationService();

    public static void updateReservation(){
        int roomNumber;
        java.util.Date begDateUtil;
        java.util.Date endDateUtil;
        String extraService;
        Scanner sc= new Scanner(System.in);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("Lütfen güncellemek istediğiniz rezervasyonunuzun oda numarasını giriniz");
        roomNumber=sc.nextInt();
        System.out.println("Lütfen güncellemek istediğiniz yeni otele giriş tarihini giriniz: (eğer aynı ise enter a basıp devam ediniz) ");
        String begDateStr = sc.next();
        System.out.println("Lütfen güncellemek istediğiniz yeni otelden çıkış tarihini giriniz: (eğer aynı ise enter a basıp devam ediniz) ");
        String endDateStr = sc.next();
        sc.nextLine();
        System.out.println("Ekstra hizmetinizi güncelleme");
        extraService = sc.nextLine();
        try {
            begDateUtil = dateFormat.parse(begDateStr);
            endDateUtil = dateFormat.parse(endDateStr);

            java.sql.Date begDateSql = new java.sql.Date(begDateUtil.getTime());
            java.sql.Date endDateSql = new java.sql.Date(endDateUtil.getTime());

            Reservation reservation= new Reservation();
            reservation.setBegDate(begDateSql);
            reservation.setEndDate(endDateSql);
            reservation.setExtraService(extraService);

            reservationService.updateReservation(roomNumber,reservation);


        } catch (ParseException e) {
            System.out.println("Tarih formatı hatalı. Lütfen yyyy-MM-dd formatını kullanın.");
            e.printStackTrace();
        }

    }
}
