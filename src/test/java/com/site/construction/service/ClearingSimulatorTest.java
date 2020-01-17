package com.site.construction.service;

import com.site.construction.constants.Command;
import com.site.construction.constants.Item;
import com.site.construction.data.CommandHistory;
import com.site.construction.simulator.ClearingSimulator;
import com.site.construction.validation.ValidationErrors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClearingSimulatorTest {
    private static ClearingSimulator clearingSimulator;
    private static ValidationErrors validationErrors;

    @BeforeEach
    public void validationErrorsSetup() {
        validationErrors = new ValidationErrors();
    }

    @AfterEach
    public void validationErrorsTearDown() {
        validationErrors = null;
    }

    @Test
    public void testClearingSimulatorBaseCase() {
        char[][] siteMap = new char[][]{
                {'o', 'o', 't', 'o', 'o', 'o', 'o', 'o', 'o', 'o'},
                {'o', 'o', 'o', 'o', 'o', 'o', 'o', 'T', 'o', 'o'},
                {'r', 'r', 'r', 'o', 'o', 'o', 'o', 'T', 'o', 'o'},
                {'r', 'r', 'r', 'r', 'o', 'o', 'o', 'o', 'o', 'o'},
                {'r', 'r', 'r', 'r', 'r', 't', 'o', 'o', 'o', 'o'}
        };
        List<CommandHistory> commands = new ArrayList<>();
        commands.add(new CommandHistory(Command.ADVANCE, 4));
        commands.add(new CommandHistory(Command.RIGHT, 0));
        commands.add(new CommandHistory(Command.ADVANCE, 4));
        commands.add(new CommandHistory(Command.LEFT, 0));
        commands.add(new CommandHistory(Command.ADVANCE, 2));
        commands.add(new CommandHistory(Command.ADVANCE, 4));
        commands.add(new CommandHistory(Command.LEFT, 0));
        commands.add(new CommandHistory(Command.QUIT, 0));

        clearingSimulator = new ClearingSimulator(siteMap);
        int i = 0;
        while (true) {
            CommandHistory commandHistory = commands.get(i++);
            boolean endSimulation = clearingSimulator.execute(commandHistory.getCommand(), commandHistory.getSteps());

            if (endSimulation) {
                //print history and scores
                clearingSimulator.calculateAndPrintHistoryAndScores(commands);
                assertEquals(validationErrors.getErrors().size(), 0);
                assertEquals(clearingSimulator.getUnclearedSquares(), 34);
                assertEquals(clearingSimulator.getScoringMap().get(Item.FUEL), 19);
                assertEquals(clearingSimulator.getScoringMap().get(Item.PAINT_DAMAGE), 1);
                assertEquals(clearingSimulator.getScoringMap().get(Item.COMMUNICATION_OVERHEAD), 7);
                assertEquals(clearingSimulator.getScoringMap().get(Item.PROTECTED_TREE_DESTRUCTION), 0);
                assertEquals(clearingSimulator.getScoringMap().get(Item.UNCLEARED_SQUARE), 34);

                return;
            }
        }
    }

    @Test
    public void testClearingSimulatorDestructionCase() {
        char[][] siteMap = new char[][]{
                {'o', 't', 'T', 'o', 'o', 'o'},
                {'o', 'o', 'o', 'o', 'o', 'o'},
                {'r', 'r', 'r', 'o', 'o', 'o'}
        };
        List<CommandHistory> commands = new ArrayList<>();
        commands.add(new CommandHistory(Command.ADVANCE, 4));
        commands.add(new CommandHistory(Command.RIGHT, 0));
        commands.add(new CommandHistory(Command.LEFT, 0));
        commands.add(new CommandHistory(Command.QUIT, 0));

        clearingSimulator = new ClearingSimulator(siteMap);
        int i = 0;
        while (true) {
            CommandHistory commandHistory = commands.get(i++);
            boolean endSimulation = clearingSimulator.execute(commandHistory.getCommand(), commandHistory.getSteps());

            if (endSimulation) {
                //print history and scores
                clearingSimulator.calculateAndPrintHistoryAndScores(commands);
                assertEquals(validationErrors.getErrors().size(), 0);
                assertEquals(clearingSimulator.getUnclearedSquares(), 15);
                assertEquals(clearingSimulator.getScoringMap().get(Item.FUEL), 3);
                assertEquals(clearingSimulator.getScoringMap().get(Item.PAINT_DAMAGE), 1);
                assertEquals(clearingSimulator.getScoringMap().get(Item.COMMUNICATION_OVERHEAD), 1);
                assertEquals(clearingSimulator.getScoringMap().get(Item.PROTECTED_TREE_DESTRUCTION), 1);
                assertEquals(clearingSimulator.getScoringMap().get(Item.UNCLEARED_SQUARE), 15);

                return;
            }
        }
    }

    @Test
    public void testClearingSimulatorOutOfBoundsCase() {
        char[][] siteMap = new char[][]{
                {'o', 't', 't', 'o', 'o', 'o'},
                {'o', 'o', 'o', 'o', 'o', 'o'},
                {'r', 'r', 'r', 'o', 'T', 'o'}
        };
        List<CommandHistory> commands = new ArrayList<>();
        commands.add(new CommandHistory(Command.ADVANCE, 4));
        commands.add(new CommandHistory(Command.RIGHT, 0));
        commands.add(new CommandHistory(Command.LEFT, 0));
        commands.add(new CommandHistory(Command.ADVANCE, 3));
        commands.add(new CommandHistory(Command.RIGHT, 0));
        commands.add(new CommandHistory(Command.ADVANCE, 3));
        commands.add(new CommandHistory(Command.QUIT, 0));

        clearingSimulator = new ClearingSimulator(siteMap);
        int i = 0;
        while (true) {
            CommandHistory commandHistory = commands.get(i++);
            boolean endSimulation = clearingSimulator.execute(commandHistory.getCommand(), commandHistory.getSteps());

            if (endSimulation) {
                //print history and scores
                clearingSimulator.calculateAndPrintHistoryAndScores(commands);
                assertEquals(validationErrors.getErrors().size(), 0);
                assertEquals(clearingSimulator.getUnclearedSquares(), 11);
                assertEquals(clearingSimulator.getScoringMap().get(Item.FUEL), 8);
                assertEquals(clearingSimulator.getScoringMap().get(Item.PAINT_DAMAGE), 2);
                assertEquals(clearingSimulator.getScoringMap().get(Item.COMMUNICATION_OVERHEAD), 4);
                assertEquals(clearingSimulator.getScoringMap().get(Item.PROTECTED_TREE_DESTRUCTION), 0);
                assertEquals(clearingSimulator.getScoringMap().get(Item.UNCLEARED_SQUARE), 11);

                return;
            }
        }
    }
}
