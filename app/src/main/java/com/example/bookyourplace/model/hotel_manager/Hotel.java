package com.example.bookyourplace.model.hotel_manager;

import com.example.bookyourplace.model.Address;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hotel implements Serializable {

    ////////////////   DATA    ////////////////
    private String manager;
    //////////////////////////////
    private String name;
    private String phone;
    //////////////////////////////
    private Address address;
    //////////////////////////////
    private int rate;
    private float stars;
    //////////////////////////////
    private int total_rooms;
    private float price;
    //////////////////////////////
    private String description;
    //////////////////////////////


    private HotelFeature feature;
    //////////////////////////////
    private String coverPhoto;
    private List<String> otherPhotos;

    private List<String> bookings;

    public Hotel() {
        ////////////////   DATA    ////////////////
        manager = "";
        //////////////////////////////
        name = "";
        phone = "";
        //////////////////////////////
        address = new Address();
        //////////////////////////////
        rate = 0;
        stars = 0;
        //////////////////////////////
        total_rooms = 0;
        price = 0;
        //////////////////////////////
        description = "";
        //////////////////////////////
        feature = new HotelFeature();
        //////////////////////////////
        coverPhoto = "";
        otherPhotos = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public Hotel(String name, String phone, String description, Address address, String manager, float price, float stars, int total_rooms, HotelFeature feature) {

        this.name = name;
        this.phone = phone;
        this.description = description;
        this.address = address;
        this.manager = manager;
        this.price = price;
        this.rate = 0;
        this.stars = stars;
        this.total_rooms = total_rooms;
        this.feature = feature;
        this.coverPhoto = "";
        this.otherPhotos = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    //////////////// GETS BEGIN ////////////////
    public String getManager() {
        return manager;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public int getRate() {
        return rate;
    }

    public float getStars() {
        return stars;
    }

    public int getTotal_Rooms() {
        return total_rooms;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public HotelFeature getFeature() {
        return feature;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public List<String> getOtherPhotos() {
        return otherPhotos;
    }

    public List<String> getBookings() {
        return bookings;
    }
    //////////////// GETS END ////////////////

    //////////////// SETS BEGIN ////////////////
    public void setManager(String manager) {
        this.manager = manager;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public void setTotal_Rooms(int total_rooms) {
        this.total_rooms = total_rooms;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFeature(HotelFeature feature) {
        this.feature = feature;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public void addBooking(String booking) {
        if (bookings == null) {
            bookings = new ArrayList<>();
        }
        this.bookings.add(booking);
    }
//////////////// SETS END ////////////////
}