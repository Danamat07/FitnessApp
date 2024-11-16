package app;

import controller.*;
import java.util.Scanner;

public class TrainerUI {

    private final FitnessController fitnessController;
    private final Scanner scanner;

    public TrainerUI(FitnessController fitnessController) {
        this.fitnessController = fitnessController;
        this.scanner = new Scanner(System.in);
        //hello
    }
}
