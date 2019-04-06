package com.logistic.logic.e_saloon.Model;

public class CouponsDetails {

    String couponTitle;
    String couponCode;
    String couponValidity;
    String imageUrl;
    String couponDetails;
    String discountPercentage;

    public CouponsDetails() {
    }

    public CouponsDetails(String couponTitle, String couponCode,
                          String couponValidity, String imageUrl) {
        this.couponTitle = couponTitle;
        this.couponCode = couponCode;
        this.couponValidity = couponValidity;
        this.imageUrl = imageUrl;
    }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getCouponDetails() {
        return couponDetails;
    }

    public void setCouponDetails(String couponDetails) {
        this.couponDetails = couponDetails;
    }

    public String getCouponTitle() {
        return couponTitle;
    }

    public void setCouponTitle(String couponTitle) {
        this.couponTitle = couponTitle;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponValidity() {
        return couponValidity;
    }

    public void setCouponValidity(String couponValidity) {
        this.couponValidity = couponValidity;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

