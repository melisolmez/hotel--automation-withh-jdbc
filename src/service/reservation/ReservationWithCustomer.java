package service.reservation;

import model.Reservation;
import model.User;

public class ReservationWithCustomer {
    private Reservation reservation;
    private User customer;

    public ReservationWithCustomer(Reservation reservation, User customer) {
        this.reservation = reservation;
        this.customer = customer;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }
}
