package com.logistic.logic.e_saloon.Appointments;

public class ServicesBookedDetails {

    String serviceName;
    String serviceDesc;
    String serviceQty;
    String servicePrice;
    String serviceTotalPrice;
    String saloonName;

    public ServicesBookedDetails() {
    }

    public ServicesBookedDetails(String serviceName, String serviceQty,
                                 String servicePrice, String serviceTotalPrice, String saloonName) {
        this.serviceName = serviceName;
        this.serviceQty = serviceQty;
        this.servicePrice = servicePrice;
        this.serviceTotalPrice = serviceTotalPrice;
        this.saloonName = saloonName;
    }

    public ServicesBookedDetails(String serviceName, String serviceDesc, String serviceQty,
                                 String servicePrice, String serviceTotalPrice, String saloonName) {
        this.serviceName = serviceName;
        this.serviceDesc = serviceDesc;
        this.serviceQty = serviceQty;
        this.servicePrice = servicePrice;
        this.serviceTotalPrice = serviceTotalPrice;
        this.saloonName = saloonName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public String getServiceQty() {
        return serviceQty;
    }

    public void setServiceQty(String serviceQty) {
        this.serviceQty = serviceQty;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceTotalPrice() {
        return serviceTotalPrice;
    }

    public void setServiceTotalPrice(String serviceTotalPrice) {
        this.serviceTotalPrice = serviceTotalPrice;
    }

    public String getSaloonName() {
        return saloonName;
    }

    public void setSaloonName(String saloonName) {
        this.saloonName = saloonName;
    }
}
