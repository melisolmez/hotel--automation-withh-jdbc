package model;


import java.sql.Date;

public class Reservation {

    private int id;
    private int roomNumber;
    private String customerTc;
    private Date begDate;
    private Date endDate;
    private String extraService;

    public String getExtraService() {
        return extraService;
    }

    public void setExtraService(String extraService) {
        this.extraService = extraService;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getCustomerTc() {
        return customerTc;
    }

    public void setCustomerTc(String customerTc) {
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


}
