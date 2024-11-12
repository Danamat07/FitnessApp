package controller;
import model.*;
import service.FitnessService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FitnessController {

    private final FitnessService fitnessService;

    // Controller constructor
    public FitnessController(FitnessService fitnessService) {
        this.fitnessService = fitnessService;
    }

}