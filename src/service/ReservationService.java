package service;

import model.Reservation;
import repository.IRepository;

import java.util.List;

public class ReservationService {
    private final IRepository<Reservation> reservationRepository;

    public ReservationService(IRepository<Reservation> reservationRepository) {
        this.reservationRepository = reservationRepository;
    }


    public Reservation getReservation(int id){
        return reservationRepository.read(id);
    }

    public void addReservation(Reservation reservation){
        if (reservationRepository.read(reservation.getID()) != null){
            throw new IllegalArgumentException("Reservation with ID" + reservation.getID() + " alredy exists");
        }
        reservationRepository.create(reservation);
    }

    public void updateReservation(Reservation reservation){
        if(reservationRepository.read((reservation.getID())) == null){
            throw new IllegalArgumentException("Reservation with ID" + reservation.getID() + " does not exist");
        }
        reservationRepository.update(reservation);
    }

    public void deleteReservation(int id){
        if (reservationRepository.read(id) == null){
            throw new IllegalArgumentException("Reservation with ID" + id + " does not exist");
        }
    }

    public List<Reservation> getAllReservations(){
        return reservationRepository.getAll();
    }
}
