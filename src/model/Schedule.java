package model;

import java.time.LocalDateTime;

public class Schedule implements Identifiable {
    private int id;
    private FitnessClass fitnessClass;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    //Constructor
    public Schedule(FitnessClass fitnessClass, LocalDateTime startTime, LocalDateTime endTime){
        this.fitnessClass = fitnessClass;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    //Getters

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

    public void setFitnessClass(FitnessClass fitnessClass) {
        this.fitnessClass = fitnessClass;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", fitnessClass=" + fitnessClass +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
