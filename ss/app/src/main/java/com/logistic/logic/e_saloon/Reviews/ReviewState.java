package com.logistic.logic.e_saloon.Reviews;

public class ReviewState {
    String numStarsReviews;
    String descReviews;
    String nameReviews;
    String cityReviews;
    String imgUrlReviews;


    public ReviewState() {
    }

    public ReviewState(String numStarsReviews, String descReviews, String nameReviews, String cityRevies) {
        this.numStarsReviews = numStarsReviews;
        this.descReviews = descReviews;
        this.nameReviews = nameReviews;
        this.cityReviews = cityRevies;
    }

    public ReviewState(String numStarsReviews, String descReviews,
                       String nameReviews, String cityReviews, String imgUrlReviews) {
        this.numStarsReviews = numStarsReviews;
        this.descReviews = descReviews;
        this.nameReviews = nameReviews;
        this.cityReviews = cityReviews;
        this.imgUrlReviews = imgUrlReviews;
    }

    public String getNumStarsReviews() {
        return numStarsReviews;
    }

    public void setNumStarsReviews(String numStarsReviews) {
        this.numStarsReviews = numStarsReviews;
    }

    public String getDescReviews() {
        return descReviews;
    }

    public void setDescReviews(String descReviews) {
        this.descReviews = descReviews;
    }

    public String getNameReviews() {
        return nameReviews;
    }

    public void setNameReviews(String nameReviews) {
        this.nameReviews = nameReviews;
    }

    public String getCityReviews() {
        return cityReviews;
    }

    public void setCityReviews(String cityReviews) {
        this.cityReviews = cityReviews;
    }

    public String getImgUrlReviews() {
        return imgUrlReviews;
    }

    public void setImgUrlReviews(String imgUrlReviews) {
        this.imgUrlReviews = imgUrlReviews;
    }
}
