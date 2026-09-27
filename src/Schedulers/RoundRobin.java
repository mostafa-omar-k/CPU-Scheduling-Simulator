package Schedulers;

import java.util.*;

public class RoundRobin extends Scheduler {
    Queue<Process> queue = new LinkedList<>();
    String processExecutionOrder = "";
    List<Process> processesResult = new ArrayList<>();
    PriorityQueue<Process> priorityQueue = new PriorityQueue<>(1, Comparator.comparingInt(a -> a.priority));
    public void start() {
        super.schedulerIO();
        run();
    }
    public void run() {
        int timeCounter = 0;
        while (true) {
            checkArrival(timeCounter);
            Process currentProcess = queue.peek();

            if (currentProcess.burstTime == 0) {
                currentProcess.turnAroundTime = (timeCounter + contextSwitchingTime) - currentProcess.arrivalTime;
                processExecutionOrder += currentProcess.name + " ";
                processesResult.add(currentProcess);
                queue.remove();

                if (queue.size() > 0) {
                    Process nextProcess = queue.peek();
                    nextProcess.waitingTime += (timeCounter - nextProcess.stopTime + 1) + contextSwitchingTime;
                    timeCounter++;
                    continue;
                } else {
                    break;
                }
            } else if (currentProcess.timer == roundRobinTimeQuantum) {
                currentProcess.stopTime = timeCounter;
                currentProcess.timer = 0;
                processExecutionOrder += currentProcess.name + " ";
                queue.add(queue.remove());
                Process nextProcess = queue.peek();
                nextProcess.waitingTime += (timeCounter - nextProcess.stopTime) + contextSwitchingTime;
                timeCounter++;
                continue;
            }
            currentProcess.burstTime--;
            currentProcess.timer++;
            timeCounter++;
        }
    }
    public void checkArrival(int z) {
        for (Process i : listOfProcess) {
            if (i.arrivalTime == z)
                priorityQueue.add(i);
        }
        while (priorityQueue.size() > 0) {
            queue.add(priorityQueue.remove());
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
            System.out.println("Turn Around Time: " + process.turnAroundTime);
            System.out.println("Waiting Time: " + process.waitingTime);
            System.out.println("------------------------------");
            sumOfWaitingTimes += process.waitingTime;
            sumOfTurnAroundTimes += process.turnAroundTime;
        }
        System.out.println("Average Waiting Time: " + sumOfWaitingTimes / processesResult.size());
        System.out.println("Average Turn Around Time: " + sumOfTurnAroundTimes / processesResult.size());
        System.out.println("Process Execution Order: " + processExecutionOrder);
        System.out.println("==============================");
    }
}
