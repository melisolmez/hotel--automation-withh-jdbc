package model;

import java.util.Set;

public class Room {

    private int id;
    private int roomCapacity;
    private int roomNumber;
    private Set<Features> features;
    private String extraFeatures;
    private boolean isEmpty;
    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
    public Set<Features> getFeature() {
        return features;
    }

    public void setFeature(Set<Features> features) {
        this.features = features;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        if (roomCapacity < 1) {
            throw new IllegalArgumentException("Room capacity must be at least 1.");
        }
        this.roomCapacity = roomCapacity;
    }

    public String getExtraFeatures() {
        return extraFeatures;
    }

    public void setExtraFeatures(String extraFeatures) {
        this.extraFeatures = extraFeatures;
    }

}
