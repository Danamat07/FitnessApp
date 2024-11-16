package app;

import controller.*;
import java.util.Scanner;

public class MemberUI {

    private final FitnessController fitnessController;
    private final Scanner scanner;

    public MemberUI(FitnessController fitnessController) {
        this.fitnessController = fitnessController;
        this.scanner = new Scanner(System.in);
    }
    //hello
}
