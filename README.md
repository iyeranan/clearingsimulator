# Clearing Simulator

Clearing Simulator is a Java program that simulates the operation of a bulldozer over a rectangular piece of land.

## Installation and Running Instructions

Clone the repository.

Open the project in your favorite IDE (make sure you have JDK 1.8 and higher to run the simulator)

ClearingSimulator.main is the entry point of the simulator, you can either run this main program as-is
or by providing a command line argument as fileName (example siteMap.txt). If you run as-is, it takes the
siteMap from the siteMap.txt enclosed within the project. 
Note : There is a restriction that currently all input files have to be within the classpath, so its best
to put them in src/main/resources/ folder. This can be enhanced to accept files with paths later.

Once the main program runs, the terminal shows the initial site map and messages and stops for command line input,
please enter appropriately for the bulldozer's next move.

Once the simulation ends, either by command or by one of the given conditions, it will print the history and the
associated costs associated in the terminal.

## Design and Approach
