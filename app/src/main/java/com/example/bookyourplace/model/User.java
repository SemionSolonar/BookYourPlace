package com.example.bookyourplace.model;



import java.io.Serializable;

public class User implements Serializable {
    ////////////////   PERSONAL DATA    ////////////////
    private String image;
    private String name;
    private String surname;
    private String birthday;
    private String phone;
    private Address address;
    ////////////////     SECURITY     ////////////////
    private String email;
    private String password;
    ////////////////    PREFERENCES    ////////////////
    private String language;
    //////////////// PAYMENT DETAILS ////////////////


    public User(){
        image = "";
        //////////////////////////////
        name = "";
        surname = "";
        birthday = "";
        //////////////////////////////
        phone = "";
        address = new Address();
        //////////////////////////////
        email = "";
        password = "";
        //////////////////////////////
        this.language = "en";
    }

    public User(String name, String surname, String email, String phone, String password) {
        image = "";
        //////////////////////////////
        this.name = name;
        this.surname = surname;
        birthday = "";
        address = new Address();
        //////////////////////////////
        this.phone = phone;
        this.email = email;
        this.password = password;
        //////////////////////////////
        this.language = "en";
    }

    public User(String name, String surname, String email, String birthday, String phone, String password, String language, String units_currency, String units_distance) {
        image = "";
        //////////////////////////////
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        //////////////////////////////
        this.phone = phone;
        address = new Address();
        //////////////////////////////
        this.email = email;
        this.password = password;
        //////////////////////////////
        this.language = language;
    }

    //////////////// GETS BEGIN ////////////////
    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPhone() {
        return phone;
    }

    public Address getAddress(){ return address;}

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLanguage() {
        return language;
    }

    //////////////// GETS END ////////////////

    //////////////// SETS BEGIN ////////////////
    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(Address address){ this.address = address;}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
