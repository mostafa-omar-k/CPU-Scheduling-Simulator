package Schedulers;

public class Process {
    String name = "";
    int arrivalTime = 0;
    int burstTime = 0;

    int originalBurstTime = 0;
    int priority = 0;
    int stopTime = arrivalTime;
    int waitingTime = 0;
    int turnAroundTime = 0;
    int timer = 0;
    int quantumTime = 0;

    int currentQuantumTime;

    public Process(
            String name,
            int arrivalTime,
            int burstTime,
            int priority,
            int stopTime,
            int waitingTime,
            int turnAroundTime,
            int quantumTime
    ) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.stopTime = stopTime;
        this.waitingTime = waitingTime;
        this.turnAroundTime = turnAroundTime;
        this.quantumTime = quantumTime;
        this.currentQuantumTime = quantumTime;
        this.originalBurstTime = burstTime;
    }
}
