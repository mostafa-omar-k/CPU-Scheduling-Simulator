package Schedulers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PreemptiveSJF extends Scheduler {
    //list that holds processes  to get it printed
    List<Process> processesResult = new ArrayList<>();
    //queue that sort process according to its priority, that helps when two processes arrives at the same time and the higher priority process comes first
    PriorityQueue<Process> priorityQueue = new PriorityQueue<>(1, Comparator.comparingInt(a -> a.priority));
    //queue that hold the processes
    PriorityQueue<Process> mainPriorityQueue = new PriorityQueue<>(1, Comparator.comparingInt(a -> a.burstTime));
    //variable that contains the process execution order
    String processExecutionOrder = "";

    @Override
    public void start() {
        super.schedulerIO();
        //variable that holds the old process before switching to another to help adding waiting time to other process because of context switching
        Process processHolder = new Process("", 0, 0, 0, 0, 0, 0,0);
        //variable that act like a timer counter
        int timeCounter = 0;
        while (true) {
            //filling a priority queue with process in listOfProcess, if two processes came at the same time, the process with higher priority is added to the queue first
            for (Process process : listOfProcess) {
                if (timeCounter == process.arrivalTime) {
                    priorityQueue.add(process);
                }
            }
            //filling mainPriorityQueue from priorityQueue
            while (priorityQueue.size() > 0) {
                mainPriorityQueue.add(priorityQueue.remove());
            }
            //to assert that the mainPriorityQueue Peek is not null
            if (!mainPriorityQueue.isEmpty()) {
                Process currentProcess = mainPriorityQueue.peek();
                //check if the previous process is still running, if not context switching happens
                if (!processHolder.name.equals(currentProcess.name)) {
                    //adding to processExecutionOrder a new process that started to operate
                    processExecutionOrder += currentProcess.name + " ";
                    // adding context switching delay time to the other process
                    for (Process process : mainPriorityQueue) {
                        if (processHolder != process && !processHolder.name.equals("")) {
                            process.waitingTime += contextSwitchingTime;
                            process.turnAroundTime += contextSwitchingTime;
                        }
                    }
                    // refresh the processHolder
                    processHolder.name = currentProcess.name;
                }
                //decrementing burstTime of current process to get notified when this process terminates
                currentProcess.burstTime--;
                //adding waiting time to other process in the mainPriorityQueue
                for (Process process : mainPriorityQueue) {
                    if (currentProcess != process) {
                        process.waitingTime++;
                    }
                }
                //pop the currentProcess form the mainPriorityQueue when it is terminated
                if (currentProcess.burstTime == 0) {
                    //calculate turn around time of the process when it terminates
                    currentProcess.turnAroundTime += timeCounter - currentProcess.arrivalTime + 1;
                    processesResult.add(mainPriorityQueue.remove());
                }
            }
            //break the loop if it is empty
            if (mainPriorityQueue.isEmpty()) break;
            //increment time
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
