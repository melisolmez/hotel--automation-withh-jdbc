package ui;

import model.Features;
import model.Room;
import service.room.DefaultRoomService;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class AddRoomUI {

    private static final DefaultRoomService roomService= new DefaultRoomService();
    public static void addRoom() {
        Scanner sc = new Scanner(System.in);
        int roomCapacity, roomNumber, count;
        Set<Features> features = new HashSet<>();
        String extraFeatures;
        double price;
        System.out.println("Lütfen kaç oda oluşturmak istediğinizi giriniz:");
        count = sc.nextInt();

        while (count > 0) {
            System.out.println("Lütfen oda'nın kapasitesini en az 1 kişilik olmak üzere giriniz:");
            roomCapacity = sc.nextInt();
            System.out.println("Lütfen oda numarasını giriniz");
            roomNumber = sc.nextInt();
            sc.nextLine();
            features.add(Features.BANYO);
            features.add(Features.WIFI);
            features.add(Features.MINIBAR);
            features.add(Features.TV);
            features.add(Features.HAVLU);
            System.out.println("Lütfen ekstra bir özellik var ise belirtiniz");
            extraFeatures = sc.nextLine();
            System.out.println("Lütfen oda fiyatını giriniz");
            price = sc.nextDouble();

            Room room = new Room();
            room.setRoomCapacity(roomCapacity);
            room.setRoomNumber(roomNumber);
            room.setFeature(features);
            room.setExtraFeatures(extraFeatures);
            room.setPrice(price);
            try {
                roomService.addRoom(room);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            count--;
        }
    }
}
