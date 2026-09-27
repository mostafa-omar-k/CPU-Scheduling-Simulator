import Schedulers.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Scheduler scheduler;

        do {
            System.out.println("Select Scheduler");
            System.out.println("1. Preemptive SJF");
            System.out.println("2. Round Robin");
            System.out.println("3. Preemptive Priority Scheduler");
            System.out.println("4. AG Scheduler");
            System.out.println("5. Terminate");

            switch (scanner.nextInt()) {
                case 1 -> {
                    scheduler = new PreemptiveSJF();
                    scheduler.start();
                    scheduler.printResult();
                }
                case 2 -> {
                    scheduler = new RoundRobin();
                    scheduler.start();
                    scheduler.printResult();
                }
                case 3 -> {
                    scheduler = new PreemptivePriorityScheduler();
                    scheduler.start();
                    scheduler.printResult();
                }
                case 4 -> {
                    scheduler = new AGScheduler();
                    scheduler.start();
                    scheduler.printResult();
                }
                case 5 -> {
                    System.out.println("Program Terminate...");
                    return;
                }
                default -> System.out.println("Please enter a valid input");
            }
        } while (true);
    }
}