package com.logistic.logic.e_saloon.Model;

public class CategoryData1 {

    String type;
    String desc;
    String price;
    String addItem;
    String saloonName;
    String imageUrl;

    public CategoryData1() {
    }

    public CategoryData1(String type, String desc, String addItem) {
        this.type = type;
        this.desc = desc;
        this.addItem = addItem;
    }

    public CategoryData1(String type, String desc, String price, String addItem) {
        this.type = type;
        this.desc = desc;
        this.price = price;
        this.addItem = addItem;
    }

    public CategoryData1(String type, String desc, String price
            , String addItem, String saloonName) {
        this.type = type;
        this.desc = desc;
        this.price = price;
        this.addItem = addItem;
        this.saloonName = saloonName;
    }

    public String getSaloonName() {
        return saloonName;
    }

    public void setSaloonName(String saloonName) {
        this.saloonName = saloonName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAddItem() {
        return addItem;
    }

    public void setAddItem(String addItem) {
        this.addItem = addItem;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
