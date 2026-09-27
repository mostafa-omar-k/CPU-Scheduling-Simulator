package Schedulers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AGScheduler extends Scheduler {
    public AGScheduler() {
        contextSwitchingTime = 0;
    }

    String processExecutionOrder = "";

    List<Process> processesResult = new ArrayList<>();

    @Override
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
            process.originalBurstTime = process.burstTime;
            System.out.print("Process Priority: ");
            process.priority = scanner.nextInt();
            System.out.print("Process Quantum Time: ");
            process.quantumTime = scanner.nextInt();
            process.currentQuantumTime = process.quantumTime;
            listOfProcess.add(process);
        }
    }

    @Override
    public void start() {
        contextSwitchingTime = 0;
        schedulerIO();
        List<Process> processList = new ArrayList<>();
        int timeCounter = 0;
        Process currentProcess = null;
        while (timeCounter < 100) {
            for (Process process : listOfProcess) {
                if (timeCounter == process.arrivalTime) {
                    processList.add(process);
                }
            }
            if (!processList.isEmpty() && null == currentProcess) {
                currentProcess = processList.get(0);
                processExecutionOrder += currentProcess.name + " ";
                processList.remove(0);
            }
            if (currentProcess != null) {
                currentProcess.burstTime--;
                currentProcess.timer++;
                currentProcess.currentQuantumTime--;
                if (currentProcess.burstTime == 0) {
                    currentProcess.turnAroundTime = timeCounter - currentProcess.arrivalTime + 1;
                    currentProcess.waitingTime = currentProcess.turnAroundTime - currentProcess.originalBurstTime;
                    processesResult.add(currentProcess);
                    System.out.println("Quantum time update for: " + currentProcess.name + " " + currentProcess.quantumTime + " -> 0");
                    currentProcess = null;
                } else if (currentProcess.currentQuantumTime == 0) {
                    currentProcess.quantumTime += 2;
                    currentProcess.currentQuantumTime = currentProcess.quantumTime;
                    currentProcess.timer = 0;
                    processList.add(currentProcess);
                    currentProcess = null;
                }
            }
            if (true) {
                if (currentProcess != null && currentProcess.timer == (int) Math.ceil(currentProcess.quantumTime * 0.25f)) {
                    processList.sort(Comparator.comparingInt(a -> a.priority));
                    if (!processList.isEmpty() && currentProcess.priority > processList.get(0).priority) {
                        System.out.print("Quantum time update for : " + currentProcess.name + " " + currentProcess.quantumTime + " -> ");
                        currentProcess.quantumTime += Math.ceil(currentProcess.currentQuantumTime / 2.0f);
                        System.out.println(currentProcess.quantumTime);
                        currentProcess.currentQuantumTime = currentProcess.quantumTime;
                        currentProcess.timer = 0;

                        processList.add(currentProcess);
                        currentProcess = null;
                    }
                } else if (currentProcess != null && currentProcess.timer >= (int) Math.ceil(currentProcess.quantumTime * 0.5f)) {
                    processList.sort(Comparator.comparingInt(a -> a.burstTime));
                    if (!processList.isEmpty() && currentProcess.burstTime > processList.get(0).burstTime) {
                        System.out.print("Quantum time update for : " + currentProcess.name + " " + currentProcess.quantumTime + " -> ");
                        currentProcess.quantumTime += currentProcess.currentQuantumTime;
                        System.out.println(currentProcess.quantumTime);
                        currentProcess.currentQuantumTime = currentProcess.quantumTime;
                        currentProcess.timer = 0;
                        processList.add(currentProcess);
                        currentProcess = null;
                    }
                }
            }
            timeCounter++;
        }
    }

    @Override
    public void printResult() {
        float sumOfWaitingTimes = 0;
        float sumOfTurnAroundTimes = 0;
        //sorting list by name before printing
        processesResult.sort(Comparator.comparing(o -> o.name));
        System.out.println("==============================");
        System.out.println("Scheduling Algorithm Execution");
        System.out.println("==============================");
        for (Process process : processesResult) {
            System.out.println("Process Name: " + process.name);
            System.out.println("Waiting Time: " + process.waitingTime);
            System.out.println("Turn Around Time: " + process.turnAroundTime);
            System.out.println("------------------------------");
            sumOfWaitingTimes += process.waitingTime;
            sumOfTurnAroundTimes += process.turnAroundTime;
        }
        System.out.println("Average Waiting Time: " + sumOfWaitingTimes / processesResult.size());
        System.out.println("Average Turn Around Time: " + sumOfTurnAroundTimes / processesResult.size());
        System.out.print("Process Execution Order: ");

        System.out.print(processExecutionOrder);
        System.out.println();
        System.out.println("==============================");
    }

}
