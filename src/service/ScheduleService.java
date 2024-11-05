package service;

import model.Schedule;
import repository.IRepository;

import java.util.List;

public class ScheduleService {
    private final IRepository<Schedule> scheduleRepository;

    public ScheduleService(IRepository<Schedule> scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule getSchedule(int id){
        return scheduleRepository.read(id);
    }

    public void addSchedule(Schedule schedule){
        if (scheduleRepository.read(schedule.getID()) != null){
            throw new IllegalArgumentException("Schedule with ID" + schedule.getID() + " alredy exists");
        }
        scheduleRepository.create(schedule);
    }

    public void updateSchedule(Schedule schedule){
        if(scheduleRepository.read((schedule.getID())) == null){
            throw new IllegalArgumentException("Schedule with ID" + schedule.getID() + " does not exist");
        }
        scheduleRepository.update(schedule);
    }

    public void deleteSchedule(int id){
        if (scheduleRepository.read(id) == null){
            throw new IllegalArgumentException("Schedule with ID" + id + " does not exist");
        }
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.getAll();
    }
}
