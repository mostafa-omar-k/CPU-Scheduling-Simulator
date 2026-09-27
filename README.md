# CPU Scheduling Simulator

A Java-based **CPU Scheduling Simulator** that implements and simulates four scheduling algorithms through an interactive console application.

## Supported Algorithms

* **Preemptive SJF**
* **Round Robin**
* **Preemptive Priority Scheduling**
* **AG Scheduling**

## Features

* Interactive process input
* Arrival Time, Burst Time, Priority, and Time Quantum
* Context Switching simulation
* Calculates:

  * Waiting Time
  * Turnaround Time
  * Average Waiting Time
  * Average Turnaround Time
* Displays Process Execution Order

## Project Structure

```text
src/
├── Main.java
└── Schedulers/
    ├── Scheduler.java
    ├── Process.java
    ├── PreemptiveSJF.java
    ├── RoundRobin.java
    ├── PreemptivePriorityScheduler.java
    └── AGScheduler.java
```

## Technologies

* Java
* Object-Oriented Programming (OOP)
* Java Collections Framework

## How to Run

Compile:

```bash
javac Main.java Schedulers/*.java
```

Run:

```bash
java Main
```

## Purpose

This project was built to practice **Operating Systems CPU Scheduling concepts** and their implementation using Java.

## Author

**Mostafa Omar Mohamed**
