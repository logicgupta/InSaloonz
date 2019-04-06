package com.logistic.logic.e_saloon.Model;

public class BookingDetails {

    String userName;
    String emailId;
    String dob;         // Date of Booking
    String doa;         // Date of Appointment
    String toa;         // Time of Appointment
    String status;
    String transactionId;
    String orderId;
    String totalPayment;
    String servicesOptedReference;


    public BookingDetails() {
    }

    public BookingDetails(String userName, String emailId, String dob,
                          String doa, String toa, String status, String totalPayment) {
        this.userName = userName;
        this.emailId = emailId;
        this.dob = dob;
        this.doa = doa;
        this.toa = toa;
        this.status = status;
        this.totalPayment = totalPayment;
    }

    public BookingDetails(String userName, String emailId, String dob, String doa, String toa, String status,
                          String orderId, String totalPayment) {
        this.userName = userName;
        this.emailId = emailId;
        this.dob = dob;
        this.doa = doa;
        this.toa = toa;
        this.status = status;
        this.orderId = orderId;
        this.totalPayment = totalPayment;
    }

    public BookingDetails(String userName, String emailId, String dob, String doa, String toa, String status,
                          String transactionId, String orderId, String totalPayment) {
        this.userName = userName;
        this.emailId = emailId;
        this.dob = dob;
        this.doa = doa;
        this.toa = toa;
        this.status = status;
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.totalPayment = totalPayment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDoa() {
        return doa;
    }

    public void setDoa(String doa) {
        this.doa = doa;
    }

    public String getToa() {
        return toa;
    }

    public void setToa(String toa) {
        this.toa = toa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getServicesOptedReference() {
        return servicesOptedReference;
    }

    public void setServicesOptedReference(String servicesOptedReference) {
        this.servicesOptedReference = servicesOptedReference;
    }
}
