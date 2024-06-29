package service.room;

import model.Room;

import java.sql.SQLException;

public interface RoomService {
   void addRoom(Room room) throws SQLException;


}
