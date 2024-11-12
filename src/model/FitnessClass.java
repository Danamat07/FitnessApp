package model;

import java.time.LocalDateTime;
import java.util.List;

public class FitnessClass implements Identifiable {
    private int id;
    private String name;
    private int duration;
    private Trainer trainer;
    private Room room;
    private int participantsCount;
    private Schedule schedule;
    private Location location;
    private List<Feedback> feedback;
    private List<Equipment> equipment;
    private List<Member> members;

    //Constructor
    public FitnessClass(String name, int duration, Trainer trainer, Room room, int participantsCount, Schedule schedule, Location location, List<Feedback> feedback, List<Member> members, List<Equipment> equipment){
        this.name = name;
        this.duration = duration;
        this.trainer = trainer;
        this.room = room;
        this.participantsCount = participantsCount;
        this.schedule = schedule;
        this.location = location;
        this.feedback = feedback;
        this.equipment = equipment;
        this.members = members;
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

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public Room getRoom() {
        return room;
    }

    public int getParticipantsCount() {
        return participantsCount;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Location getLocation() {
        return location;
    }

    public List<Feedback> getFeedback() {
        return feedback;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public List<Member> getMembers() {
        return members;
    }

    public LocalDateTime getStartTime() {
        if (schedule != null) {
            return schedule.getStartTime();
        }
        return null;
    }

    public LocalDateTime getEndTime() {
        if (schedule != null) {
            return schedule.getEndTime();
        }
        return null;
    }

    //Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setParticipantsCount(int participantsCount) {
        this.participantsCount = participantsCount;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setFeedback(List<Feedback> feedback) {
        this.feedback = feedback;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "FitnessClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", trainer=" + trainer +
                ", room=" + room +
                ", participantsCount=" + participantsCount +
                ", schedule=" + schedule +
                ", location=" + location +
                ", feedback=" + feedback +
                ", equipment=" + equipment +
                ", members=" + members +
                '}';
    }
}
