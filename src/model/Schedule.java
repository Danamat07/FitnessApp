package model;

import java.time.LocalDateTime;

public class Schedule implements Identifiable {
    private int scheduleID;
    private FitnessClass fitnessClass;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    //Constructor
    public Schedule(int scheduleID, FitnessClass fitnessClass, LocalDateTime startTime, LocalDateTime endTime){
        this.scheduleID = scheduleID;
        this.fitnessClass = fitnessClass;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public int getID() {
        return scheduleID;
    }

    //Getters

    public int getScheduleID() {
        return scheduleID;
    }

    public FitnessClass getFitnessClass() {
        return fitnessClass;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    //Setters

    public void setScheduleID(int scheduleID) {
        this.scheduleID = this.scheduleID;
    }

    public void setFitnessClass(FitnessClass fitnessClass) {
        this.fitnessClass = fitnessClass;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
