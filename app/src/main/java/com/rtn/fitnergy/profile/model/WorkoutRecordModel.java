package com.rtn.fitnergy.profile.model;

import java.util.Date;

public class WorkoutRecordModel {

    private int workoutID;
    private String workoutPlanName;
    private float workoutDuration;
    private int calorieBurnt;
    private Date workoutDate;

    public WorkoutRecordModel(int workoutID, Date workoutDate) {
        this.workoutID = workoutID;
        this.workoutDate = workoutDate;
    }

    public int getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(int workoutID) {
        this.workoutID = workoutID;
    }

    public String getWorkoutPlanName() {
        return workoutPlanName;
    }

    public void setWorkoutPlanName(String workoutPlanName) {
        this.workoutPlanName = workoutPlanName;
    }

    public float getWorkoutDuration() {
        return workoutDuration;
    }

    public void setWorkoutDuration(float workoutDuration) {
        this.workoutDuration = workoutDuration;
    }

    public int getCalorieBurnt(){
        return calorieBurnt;
    }

    public void setCalorieBurnt(int calorieBurnt){
        this.calorieBurnt = calorieBurnt;
    }

    public Date getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(Date workoutDate) {
        this.workoutDate = workoutDate;
    }

}
