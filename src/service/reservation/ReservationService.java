package service.reservation;

import model.Reservation;
import model.User;

import java.sql.SQLException;
import java.util.List;

public interface ReservationService {
    void makeReservation(Reservation reservation);
    List<ReservationWithCustomer> listMakeReservation() throws SQLException;
    void updateReservation(int id,Reservation reservation);
    List<Reservation> listReservationByUserTc(String userTc) throws SQLException;

}
