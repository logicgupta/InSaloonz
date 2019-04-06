package com.logistic.logic.e_saloon.Model;

import java.util.Comparator;

public class SaloonDetails {

    String saloon_Name;
    String saloonImageUrl;
    String saloon_Rating;
    String saloon_Location;
    String saloon_Address;
    String saloon_key;
    String mobile_Number;
    int totalPrice;

    String shopTiming;
    String typeMaleOrFemale;

    public SaloonDetails() {
    }

    public SaloonDetails(String saloon_Name, String saloonImageUrl
            , String saloon_Rating, String saloon_Address) {
        this.saloon_Name = saloon_Name;
        this.saloonImageUrl = saloonImageUrl;
        this.saloon_Rating = saloon_Rating;
        this.saloon_Address = saloon_Address;
    }

    public SaloonDetails(String saloon_Name, String saloonImageUrl, String saloon_Rating
            , String saloon_Location, String saloon_Address, String saloon_key, String mobile_Number) {
        this.saloon_Name = saloon_Name;
        this.saloonImageUrl = saloonImageUrl;
        this.saloon_Rating = saloon_Rating;
        this.saloon_Location = saloon_Location;
        this.saloon_Address = saloon_Address;
        this.saloon_key = saloon_key;
        this.mobile_Number=mobile_Number;
    }

    public SaloonDetails(String saloon_Name, String saloonImageUrl
            , String saloon_Rating, String saloon_Location, String saloon_Address) {
        this.saloon_Name = saloon_Name;
        this.saloonImageUrl = saloonImageUrl;
        this.saloon_Rating = saloon_Rating;
        this.saloon_Location = saloon_Location;
        this.saloon_Address = saloon_Address;
    }

    public SaloonDetails(String saloon_Name, String saloonImageUrl,
                         String saloon_Rating, String saloon_Location,
                         String saloon_Address, String saloon_key,
                         String mobile_Number, int totalPrice) {
        this.saloon_Name = saloon_Name;
        this.saloonImageUrl = saloonImageUrl;
        this.saloon_Rating = saloon_Rating;
        this.saloon_Location = saloon_Location;
        this.saloon_Address = saloon_Address;
        this.saloon_key = saloon_key;
        this.mobile_Number = mobile_Number;
        this.totalPrice = totalPrice;
    }

    public static final Comparator<SaloonDetails> BY_ALPHABET=
            new Comparator<SaloonDetails>() {
        @Override
        public int compare(SaloonDetails o1, SaloonDetails o2) {
            return o1.saloon_Name.compareToIgnoreCase(o2.saloon_Name);
        }
    };

    public static final Comparator<SaloonDetails> BY_PRICE_LOW=
            new Comparator<SaloonDetails>() {
        @Override
        public int compare(SaloonDetails s1, SaloonDetails s2) {
            return Integer.compare(s1.totalPrice,s2.totalPrice);
        }
    };
    public static final Comparator<SaloonDetails> BY_PRICE_HIGH=
            new Comparator<SaloonDetails>() {
                @Override
                public int compare(SaloonDetails s1, SaloonDetails s2) {
                    return Integer.compare(s2.totalPrice,s1.totalPrice);
                }
            };

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getMobile_Number() {
        return mobile_Number;
    }

    public void setMobile_Number(String mobile_Number) {
        this.mobile_Number = mobile_Number;
    }

    public String getSaloon_key() {
        return saloon_key;
    }

    public void setSaloon_key(String saloon_key) {
        this.saloon_key = saloon_key;
    }

    public String getSaloon_Name() {
        return saloon_Name;
    }

    public void setSaloon_Name(String saloon_Name) {
        this.saloon_Name = saloon_Name;
    }

    public String getSaloonImageUrl() {
        return saloonImageUrl;
    }

    public void setSaloonImageUrl(String saloonImageUrl) {
        this.saloonImageUrl = saloonImageUrl;
    }

    public String getSaloon_Rating() {
        return saloon_Rating;
    }

    public void setSaloon_Rating(String saloon_Rating) {
        this.saloon_Rating = saloon_Rating;
    }

    public String getSaloon_Location() {
        return saloon_Location;
    }

    public void setSaloon_Location(String saloon_Location) {
        this.saloon_Location = saloon_Location;
    }

    public String getSaloon_Address() {
        return saloon_Address;
    }

    public void setSaloon_Address(String saloon_Address) {
        this.saloon_Address = saloon_Address;
    }

    public String getShopTiming() {
        return shopTiming;
    }

    public void setShopTiming(String shopTiming) {
        this.shopTiming = shopTiming;
    }

    public String getTypeMaleOrFemale() {
        return typeMaleOrFemale;
    }

    public void setTypeMaleOrFemale(String typeMaleOrFemale) {
        this.typeMaleOrFemale = typeMaleOrFemale;
    }
}