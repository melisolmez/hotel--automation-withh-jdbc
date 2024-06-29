package service.reservation;

import model.Reservation;
import model.User;

import java.util.List;

public interface ReservationService {
    void makeReservation(Reservation reservation);
    List<ReservationWithCustomer> listMakeReservation();
}
