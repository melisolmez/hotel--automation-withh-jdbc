package model;

import java.sql.Date;
import java.time.LocalDate;

public class Reservation {

    private int id;
    private int roomId;
    private String customerTc;
    private Date begDate;
    private Date endDate;
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getCustomerId() {
        return customerTc;
    }

    public void setCustomerId(String customerTc) {
        this.customerTc = customerTc;
    }

    public Date getBegDate() {
        return begDate;
    }

    public void setBegDate(Date begDate) {
        this.begDate = begDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
