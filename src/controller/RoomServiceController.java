package controller;

import model.Features;
import model.User;
import service.room.DefaultRoomService;
import service.user.DefaultUserService;
import service.user.UserService;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class RoomServiceController {

    private final DefaultRoomService roomService= new DefaultRoomService();
    private final UserService userService= new DefaultUserService();
    public void addRoom(){


        User user= new User();
        user.setName("melis");
        user.setSurname("olmez");
        user.setTC("11111111111");
        user.setTel("05312141414");
        try{
            userService.register(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        /*
        RoomServiceRequest room= new RoomServiceRequest();
        room.setRoomNumber(1);
        room.setRoomCapacity(2);

        Set<Features> features = new HashSet<>();
        features.add(Features.BANYO);
        features.add(Features.WIFI);
        features.add(Features.MINIBAR);
        features.add(Features.TV);
        features.add(Features.HAVLU);
        room.setFeature(features);
        room.setExtraFeatures("deniz manzaralı");

        try{
            roomService.addRoom(room);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

         */

    }



}
