package model;

import java.time.LocalDateTime;

public class Schedule {
    private int scheduleId;
    private FitnessClass fitnessClass;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    //Constructor
    public Schedule(int scheduleId, FitnessClass fitnessClass, LocalDateTime startTime, LocalDateTime endTime){
        this.scheduleId = scheduleId;
        this.fitnessClass = fitnessClass;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    //Getters

    public int getScheduleId() {
        return scheduleId;
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

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
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
