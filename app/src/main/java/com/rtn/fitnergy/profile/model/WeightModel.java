package com.rtn.fitnergy.profile.model;

import java.util.Date;

public class WeightModel {
    private String userEmail;
    private Date date;
    private float previousWeight;
    private float currentWeight;

    public WeightModel(String userEmail, Date date, float previousWeight, float currentWeight) {
        this.date = date;
        this.userEmail = userEmail;
        this.previousWeight = previousWeight;
        this.currentWeight = currentWeight;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    public float getPreviousWeight() {
        return previousWeight;
    }

    public void setPreviousWeight(float previousWeight) {
        this.previousWeight = previousWeight;
    }

    public float getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(float currentWeight) {
        this.currentWeight = currentWeight;
    }

    public float getWeightChange() {
        return currentWeight - previousWeight;
    }
}
