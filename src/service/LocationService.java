package service;

import model.Location;
import repository.IRepository;

import java.util.List;

public class LocationService {

    private final IRepository<Location> LocationRepository;

    public LocationService(IRepository<Location> LocationRepository) {
        this.LocationRepository = LocationRepository;
    }

    public Location getLocation(int id){
        return LocationRepository.read(id);
    }

    public void addLocation(Location Location){
        if (LocationRepository.read(Location.getID()) != null){
            throw new IllegalArgumentException("Location with ID" + Location.getID() + " alredy exists");
        }
        LocationRepository.create(Location);
    }

    public void updateLocation(Location Location){
        if(LocationRepository.read((Location.getID())) == null){
            throw new IllegalArgumentException("Location with ID" + Location.getID() + " does not exist");
        }
        LocationRepository.update(Location);
    }

    public void deleteLocation(int id){
        if (LocationRepository.read(id) == null){
            throw new IllegalArgumentException("Location with ID" + id + " does not exist");
        }
    }

    public List<Location> getAllLocations(){
        return LocationRepository.getAll();
    }
}
