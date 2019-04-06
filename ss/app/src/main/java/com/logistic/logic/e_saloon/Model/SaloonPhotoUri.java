package com.logistic.logic.e_saloon.Model;

public class SaloonPhotoUri {
    String saloonphotouri;
    String photoKey;


public SaloonPhotoUri() {
        }

public SaloonPhotoUri(String saloonphotouri, String photoKey) {
        this.saloonphotouri = saloonphotouri;
        this.photoKey = photoKey;
        }

public String getPhotoKey() {
        return photoKey;
        }

public void setPhotoKey(String photoKey) {
        this.photoKey = photoKey;
        }


public String getSaloonphotouri() {
        return saloonphotouri;
        }

public void setSaloonphotouri(String saloonphotouri) {
        this.saloonphotouri = saloonphotouri;
        }
        }
