package service.room;

import model.Room;

import java.sql.SQLException;
import java.util.List;

public interface RoomService {
   void addRoom(Room room) throws SQLException;
   List<Room> listSuitableRoom() throws SQLException;


}
