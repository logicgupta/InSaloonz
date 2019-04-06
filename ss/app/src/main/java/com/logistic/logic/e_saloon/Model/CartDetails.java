package com.logistic.logic.e_saloon.Model;

public class CartDetails {

    String serviceType;
    String serviceDesc;
    String serviceOriginalPrice;
    String serviceTotalPrice;
    String serviceQty;
    String docKey;
    String saloonName;
    String imageUrl;
    public CartDetails() {
    }

    public CartDetails(String serviceType, String serviceDesc, String serviceOriginalPrice
            , String serviceTotalPrice, String serviceQty, String docKey) {
        this.serviceType = serviceType;
        this.serviceDesc = serviceDesc;
        this.serviceOriginalPrice = serviceOriginalPrice;
        this.serviceTotalPrice = serviceTotalPrice;
        this.serviceQty = serviceQty;
        this.docKey = docKey;
    }

    public CartDetails(String serviceType, String serviceDesc, String serviceOriginalPrice,
                       String serviceTotalPrice, String serviceQty, String docKey, String saloonName) {
        this.serviceType = serviceType;
        this.serviceDesc = serviceDesc;
        this.serviceOriginalPrice = serviceOriginalPrice;
        this.serviceTotalPrice = serviceTotalPrice;
        this.serviceQty = serviceQty;
        this.docKey = docKey;
        this.saloonName = saloonName;
    }

    public String getSaloonName() {
        return saloonName;
    }

    public void setSaloonName(String saloonName) {
        this.saloonName = saloonName;
    }

    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public String getServiceQty() {
        return serviceQty;
    }

    public void setServiceQty(String serviceQty) {
        this.serviceQty = serviceQty;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public String getServiceOriginalPrice() {
        return serviceOriginalPrice;
    }

    public void setServiceOriginalPrice(String serviceOriginalPrice) {
        this.serviceOriginalPrice = serviceOriginalPrice;
    }

    public String getServiceTotalPrice() {
        return serviceTotalPrice;
    }

    public void setServiceTotalPrice(String serviceTotalPrice) {
        this.serviceTotalPrice = serviceTotalPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
