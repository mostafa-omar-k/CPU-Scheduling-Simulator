package Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Scheduler {
    protected final Scanner scanner = new Scanner(System.in);
    int numberOfProcesses = 0;
    int roundRobinTimeQuantum = 0;
    int contextSwitchingTime = 0;
    List<Process> listOfProcess = new ArrayList<>();

    /**
     * taking process data from the user
     */

    public void schedulerIO() {
        System.out.print("Enter Number of processes: ");
        numberOfProcesses = scanner.nextInt();

        System.out.print("Enter Round robin Time Quantum: ");
        roundRobinTimeQuantum = scanner.nextInt();

        System.out.print("Enter Context switching: ");
        contextSwitchingTime = scanner.nextInt();

        for (int i = 0; i < numberOfProcesses; i++) {
            Process process = new Process("", 0, 0, 0, 0, 0, 0, 0);
            System.out.print("Enter Process (" + (i + 1) + ") Name: ");
            process.name = scanner.next();
            System.out.print("Enter Process Arrival Time: ");
            process.arrivalTime = scanner.nextInt();
            process.stopTime = process.arrivalTime;
            System.out.print("Process Burst Time: ");
            process.burstTime = scanner.nextInt();
            System.out.print("Process Priority: ");
            process.priority = scanner.nextInt();
            listOfProcess.add(process);
        }
    }

    /**
     * starting to implement the scheduling algorithm
     */
    public abstract void start();

    /**
     * printing the result of scheduling simulation algorithm
     */
    public abstract void printResult();
}
