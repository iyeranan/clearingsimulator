package com.site.construction.simulator;

import com.site.construction.constants.Block;
import com.site.construction.constants.Command;
import com.site.construction.constants.Item;
import com.site.construction.data.Bulldozer;
import com.site.construction.data.CommandHistory;
import com.site.construction.data.Location;
import com.site.construction.constants.Orientation;
import com.site.construction.service.*;
import com.site.construction.validation.ValidationErrors;

import java.util.*;

//Main class that runs the clearing simulator program
public class ClearingSimulator {
    private final char[][] siteMap;
    private Map<Item, Integer> scoringMap = new HashMap<>();
    private final int rows;
    private final int columns;
    private int unclearedSquares = 0;
    private final Bulldozer bulldozer;
    private final CommandService commandService = new BulldozerCommandServiceImpl();
    private final VehicleService vehicleService = new BulldozerServiceImpl();


    public int getUnclearedSquares() {
        return this.unclearedSquares;
    }

    public Map<Item, Integer> getScoringMap() {
        return this.scoringMap;
    }

    //constructor
    public ClearingSimulator(char[][] siteMap) {
        this.siteMap = siteMap;
        this.rows = siteMap.length;
        this.columns = siteMap[0].length;
        this.bulldozer = new Bulldozer(new Location(0, -1), Orientation.RIGHT);

        for (char[] sites : siteMap) {
            for (char site : sites) {
                if (site != Block.PRESERVED_LAND.getValue()) {
                    this.unclearedSquares++;
                }
            }
        }
        Arrays.stream(Item.values()).forEach(item -> {
            scoringMap.put(item, 0);
        });
    }

    //main method that serves as entry point to the simulator
    public static void main(String[] args) {
        //this object stores the list of validation errors across different steps in the simulation.
        ValidationErrors validationErrors = new ValidationErrors();
        SiteMapService siteMapService = new SiteMapServiceImpl();

        //the input file can be entered as a command line argument to the program with file extension. If not entered, the default is siteMap.txt in the project folder
        //Current restriction processes the file as Resource from the classpath, so please create the file under src/main/resources folder.
        String fileName = "siteMap.txt";
        if (args != null && args.length > 0 && args[0] != null) {
            fileName = args[0];
        }

        //read the input file line by line, validate in the process
        List<String> inputLines = siteMapService.processInputFile(fileName, validationErrors);
        if (validationErrors.getErrors().size() > 0) {
            System.out.println(validationErrors.getErrors().get(0));
            return;
        }

        //form a site map character array from the extracted input lines, validate in the process
        char[][] siteMap = siteMapService.generateSiteMap(inputLines, validationErrors);
        if (validationErrors.getErrors().size() > 0) {
            System.out.println(validationErrors.getErrors().get(0));
            return;
        }

        //initialize simulator
        ClearingSimulator clearingSimulator = new ClearingSimulator(siteMap);

        System.out.println("Welcome to the Aconex site clearing simulator. This is a map of the site:");
        System.out.println();
        siteMapService.printSiteMap(siteMap);
        System.out.println();
        System.out.println("The bulldozer is currently located at the Northern edge of the site, immediately to the West of the site, and facing East.");
        System.out.println();

        // run the simulation
        clearingSimulator.simulate(validationErrors);
    }

    public void simulate(ValidationErrors validationErrors) {
        List<CommandHistory> commands = new ArrayList<>();

        while (true) {
            //Accept user input command by command
            System.out.println("(l)eft, (r)ight, (a)dvance <n>, (q)uit:");
            Scanner myObj = new Scanner(System.in);
            String input = myObj.nextLine();

            //generate bulldozer command from text input, validate for errors
            CommandHistory commandHistory = commandService.generateCommandFromInput(input, validationErrors);
            if (validationErrors.getErrors().size() > 0) {
                System.out.println(validationErrors.getErrors().get(0));
                validationErrors.getErrors().clear();
                continue;
            }

            commands.add(commandHistory);

            //execute the command and check if the current command ended the execution
            boolean endSimulation = execute(commandHistory.getCommand(), commandHistory.getSteps());

            //if simulation ended, calculate and print history and results and exit simulator.
            if (endSimulation) {
                //print history and scores
                calculateAndPrintHistoryAndScores(commands);
                return;
            }
        }
    }

    public void calculateAndPrintHistoryAndScores(List<CommandHistory> commands) {
        this.scoringMap.put(Item.UNCLEARED_SQUARE, this.unclearedSquares);

        //print history
        System.out.println("The simulation has ended at your request. These are the commands you issued:");
        System.out.println();
        System.out.println(commandService.printHistory(commands));
        System.out.println();

        //print results
        System.out.println("The costs for this land clearing operation were:");
        System.out.println();

        System.out.format("%32s%12s%12s", "Item", "Quantity", "Cost");
        System.out.println();
        int total = 0;

        for (Map.Entry<Item, Integer> entry : this.scoringMap.entrySet()) {
            int credits = entry.getValue() * entry.getKey().getCredits();
            total += credits;
            System.out.format("%32s%12s%12s", entry.getKey().getDescription(), entry.getValue(), credits);
            System.out.println();
        }
        System.out.format("%32s%12s%12s", "---------", "", "");
        System.out.println();
        System.out.format("%32s%12s%12s", "Total", "", total);
        System.out.println();
        System.out.println();
        System.out.println("Thank you for using the Aconex site clearing simulator.");
    }

    //returns true if simulation has ended
    public boolean execute(Command command, int steps) {
        if (siteMap == null || siteMap.length == 0) {
            return true;
        }

        //run business rules based on input command and move bulldozer accordingly. If Advance step, change bulldozer location and orientation.
        //If Right or Left movement, only change orientation. Allocate points in scoring Map accordingly.
        switch (command) {
            case LEFT:
            case RIGHT:
                scoringMap.put(Item.COMMUNICATION_OVERHEAD, scoringMap.get(Item.COMMUNICATION_OVERHEAD) + 1);//add communication overhead costs
                bulldozer.setOrientation(vehicleService.changeOrientation(bulldozer.getOrientation(), command));
                break;
            case ADVANCE:
                scoringMap.put(Item.COMMUNICATION_OVERHEAD, scoringMap.get(Item.COMMUNICATION_OVERHEAD) + 1);//add communication overhead costs
                for (int i = 1; i <= steps; i++) {
                    bulldozer.setCurrentLocation(vehicleService.takeStep(bulldozer.getOrientation(), bulldozer.getCurrentLocation()));
                    Location currentLocation = bulldozer.getCurrentLocation();
                    if (vehicleService.isOutOfBounds(currentLocation, this.rows, this.columns)) {
                        return true;
                    }
                    Block block = Block.retrieveByValue(this.siteMap[currentLocation.getRow()][currentLocation.getColumn()]);
                    switch (block) {
                        case PLAIN_LAND:
                            scoringMap.put(Item.FUEL, scoringMap.get(Item.FUEL) + Block.PLAIN_LAND.getFuelUnits());
                            this.siteMap[currentLocation.getRow()][currentLocation.getColumn()] = Block.CLEARED_LAND.getValue();
                            this.unclearedSquares--;
                            break;
                        case CLEARED_LAND:
                            scoringMap.put(Item.FUEL, scoringMap.get(Item.FUEL) + Block.CLEARED_LAND.getFuelUnits());
                            break;
                        case ROCKY_LAND:
                            scoringMap.put(Item.FUEL, scoringMap.get(Item.FUEL) + Block.ROCKY_LAND.getFuelUnits());
                            this.siteMap[currentLocation.getRow()][currentLocation.getColumn()] = Block.CLEARED_LAND.getValue();
                            this.unclearedSquares--;
                            break;
                        case TREE_LAND:
                            scoringMap.put(Item.FUEL, scoringMap.get(Item.FUEL) + Block.TREE_LAND.getFuelUnits());
                            if (i < steps) {
                                scoringMap.put(Item.PAINT_DAMAGE, scoringMap.get(Item.PAINT_DAMAGE) + 1);
                            }
                            this.siteMap[currentLocation.getRow()][currentLocation.getColumn()] = Block.CLEARED_LAND.getValue();
                            this.unclearedSquares--;
                            break;
                        case PRESERVED_LAND:
                            scoringMap.put(Item.PROTECTED_TREE_DESTRUCTION, scoringMap.get(Item.PROTECTED_TREE_DESTRUCTION) + 1);
                            return true;
                    }
                }
                break;
            case QUIT:
                return true;
        }

        return false;
    }

}