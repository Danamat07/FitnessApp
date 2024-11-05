package service;

import model.Room;
import repository.IRepository;
import java.util.List;

public class RoomService {

    private final IRepository<Room> roomRepository;

    public RoomService(IRepository<Room> roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room getRoom(int id){
        return roomRepository.read(id);
    }

    public void addRoom(Room room){
        if (roomRepository.read(room.getID()) != null){
            throw new IllegalArgumentException("Room with ID" + room.getID() + " alredy exists");
        }
        roomRepository.create(room);
    }

    public void updateRoom(Room room){
        if(roomRepository.read((room.getID())) == null){
            throw new IllegalArgumentException("Room with ID" + room.getID() + " does not exist");
        }
        roomRepository.update(room);
    }

    public void deleteRoom(int id){
        if (roomRepository.read(id) == null){
            throw new IllegalArgumentException("Room with ID" + id + " does not exist");
        }
    }

    public List<Room> getAllRooms(){
        return roomRepository.getAll();
    }
}
