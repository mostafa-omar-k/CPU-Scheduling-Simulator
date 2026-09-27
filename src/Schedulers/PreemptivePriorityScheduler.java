package Schedulers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PreemptivePriorityScheduler extends Scheduler {
    //list that holds processes  to get it printed
    List<Process> processesResult = new ArrayList<>();
    //arrayList to arrange the processes inside
    List<Process> processesContainer = new ArrayList<>();
    //variable that contains the process execution order
    List<String> processExecutionOrder = new ArrayList<>();

    public void start() {
        //prompt precess data from the user
        super.schedulerIO();
        //variable that act like a timer counter
        int timeCounter = 0;
        while (true) {
            //adding process to the listOfProcess
            for (Process process : listOfProcess) {
                if (timeCounter == process.arrivalTime) {
                    processesContainer.add(process);
                }
            }

            Process processesContainerPeekBeforeSorting = processesContainer.get(0);
            //sorting the processesContainer according to the priority
            processesContainer.sort(Comparator.comparingInt(a -> a.priority));
            //to fill the processesExecutionOrder
            if (processExecutionOrder.isEmpty()) {
                processExecutionOrder.add(processesContainer.get(0).name);

            } else {
                if (!processesContainer.get(0).name.equals(processExecutionOrder.get(processExecutionOrder.size() - 1))) {
                    //adding process name to processExecution order
                    processExecutionOrder.add(processesContainer.get(0).name);
                    //adding context switching time to the waiting time and the turn around time
                    for (Process process : processesContainer) {
                        process.waitingTime += contextSwitchingTime;
                        process.turnAroundTime += contextSwitchingTime;
                    }
                }
            }

            if (processesContainer.get(0) != processesContainerPeekBeforeSorting) {
                //sorting the processesContainer according to the priority and the burst time
                processesContainer.sort((a, b) -> {
                    if (a.priority == b.priority && a.burstTime < b.burstTime)
                        return a.burstTime - b.burstTime;
                    return 0;
                });
            }
            //protect the program from null pointer exception on removing form the processesContainer
            if (!processesContainer.isEmpty()) {
                for (Process process : processesContainer) {
                    if (process != processesContainer.get(0)) {
                        process.priority--;
                        process.waitingTime++;
                    }
                }
                //decrementing burstTime when the process use the cpu
                processesContainer.get(0).burstTime--;

                if (processesContainer.get(0).burstTime == 0) {
                    //calculate turn around time of the process when it terminates
                    processesContainer.get(0).turnAroundTime += timeCounter - processesContainer.get(0).arrivalTime + 1;

                    processesResult.add(processesContainer.get(0));
                    processesContainer.remove(processesContainer.get(0));
                }
            }
            //break when all processes terminate
            if (processesContainer.size() == 0)
                break;
            //increment the timeCounter
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

        for (String processName : processExecutionOrder) {
            System.out.print(processName + " ");
        }

        System.out.println();
        System.out.println("==============================");
    }
}
