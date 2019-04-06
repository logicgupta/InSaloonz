package com.logistic.logic.e_saloon.Model;

public class PersonalDetails {
    String name;
    String address;
    String phoneNumber;
    String imageUrl;

    public PersonalDetails() {
    }

    public PersonalDetails(String name, String address, String phoneNumber, String imageUrl) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
    }

    public PersonalDetails(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
