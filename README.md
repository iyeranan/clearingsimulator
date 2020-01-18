# Clearing Simulator

Clearing Simulator is a Java program that simulates the operation of a bulldozer over a rectangular piece of land.

## Installation and Running Instructions

Clone the repository.

Open the project in your favorite IDE (make sure you have JDK 1.8 and higher to run the simulator). This is a Maven project and all dependencies used are in pom.xml

com.site.construction.simulator.ClearingSimulator.main method is the entry point of the simulator, you can either run this main program as-is or by providing a command line argument as fileName (example siteMap.txt). If you run as-is, it takes the siteMap from the siteMap.txt file enclosed within the project. 
Note : There is a restriction that currently all input files have to be within the classpath, so its best
to put them in src/main/resources/ folder. This can be enhanced to accept files with paths later.

Once the main program runs, the terminal shows the initial site map and messages and stops for command line input,
please enter appropriately for the bulldozer's next move.

Once the simulation ends, either by command or by one of the given conditions, it will print the history and the
associated costs associated in the terminal.

## Design and Approach
All source code is in the src/main folder

It is natural to imagine the site Map as a 2D array with each cell holding a Block of Land.

The package structure is divided into: 
1) constants (for enums such as Block Type, Command, Item and 
Bulldozer Orientation)
2) data for central objects such as Bulldozer, Location and Command History
3) service, business services that perform various actions
4) simulator for main simulator class 
5) validation, for hosting validation errors

The simulator first creates and prints a 2D Site Map given the input text file.

It then runs an infinite loop accepting commands from the user for next actions.
Each action triggers an execute method which moves the bulldozer either in
location or orientation. 

Each execution runs the series of business rules as per the requirement and 
also keeps a tab of the squares cleared and the cost in a scoring Map with the
associated keys. Every block of land that is cleared is marked with a special C character to not double-count the land clearance.

Post execution, if the simulation needs to end in case any of the end conditions
are met, the costs are then aggregated and printed to the console. This also marks the end
of the outer loop.

Comments are provided in the code as needed and validations are done in every
possible part of the code to avoid anomalies.

## Tests
All tests are in the src/test folder

Tests are per business service implementation and cover a range of base cases,
error and boundary conditions.

These tests can all run without providing any inputs. Some tests use the siteMap files
directly from the src/test/resources folder.
