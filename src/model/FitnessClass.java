package model;

import java.time.LocalDateTime;
import java.util.List;

public class FitnessClass implements Identifiable {
    private int id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Trainer trainer;
    private Room room;
    private int participantsCount;
    private Location location;
    private List<Feedback> feedback;
    private List<Equipment> equipment;
    private List<Member> members;

    //Constructor
    public FitnessClass(String name, LocalDateTime startTime, LocalDateTime endTime, Trainer trainer, Room room, int participantsCount, Location location, List<Feedback> feedback, List<Member> members, List<Equipment> equipment){
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.trainer = trainer;
        this.room = room;
        this.participantsCount = participantsCount;
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

    public LocalDateTime getStartTime() {return startTime;}

    public LocalDateTime getEndTime() {
        return endTime;
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

    //Setters

    public void setName(String name) {
        this.name = name;
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

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "FitnessClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", trainer=" + (trainer != null ? trainer.getName() : "null") +
                ", room=" + room +
                ", participantsCount=" + participantsCount +
                ", location=" + (location != null ? location.getName() : "null") +
                ", equipment=" + (equipment != null ? equipment.size() : 0) +
                ", membersCount=" + (members != null ? members.size() : 0) +
                '}';
    }

    public String toStringLessInfo() {
        return "FitnessClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", trainer=" + trainer +
                ", room=" + room +
                ", location=" + location +
                '}';
    }
}
