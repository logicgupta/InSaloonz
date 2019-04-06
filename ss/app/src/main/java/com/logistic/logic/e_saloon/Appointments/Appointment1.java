package com.logistic.logic.e_saloon.Appointments;

public class Appointment1 {

    String orderId;
    String orderDate;
    String appointmentDate;
    String appointmentTime;
    String key;

    public Appointment1() {
    }

    public Appointment1(String orderId, String orderDate) {
        this.orderId = orderId;
        this.orderDate = orderDate;
    }

    public Appointment1(String orderId, String orderDate,
                        String appointmentDate, String appointmentTime) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
