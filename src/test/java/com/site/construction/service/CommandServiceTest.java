package com.site.construction.service;

import com.site.construction.constants.Command;
import com.site.construction.data.CommandHistory;
import com.site.construction.validation.ValidationErrors;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CommandServiceTest {
    private static CommandService commandService;
    private static ValidationErrors validationErrors;

    @BeforeAll
    public static void setUp() {
        commandService = new BulldozerCommandServiceImpl();
    }

    @BeforeEach
    public void validationErrorsSetup() {
        validationErrors = new ValidationErrors();
    }

    @Test
    public void testInvalidCommandEntered() {
        CommandHistory commandHistory = commandService.generateCommandFromInput("diagonal", validationErrors);
        assertNull(commandHistory);
        assertEquals(validationErrors.getErrors().size(), 1);
        assertEquals(validationErrors.getErrors().get(0), "ERROR: Invalid command entered");
    }

    @Test
    public void testAdvanceCommandWithoutSteps() {
        CommandHistory commandHistory = commandService.generateCommandFromInput("advance", validationErrors);
        assertNull(commandHistory);
        assertEquals(validationErrors.getErrors().size(), 1);
        assertEquals(validationErrors.getErrors().get(0), "ERROR: For Advance commands, please enter steps to advance");
    }

    @Test
    public void testAdvanceCommandWithNonNumericSteps() {
        CommandHistory commandHistory = commandService.generateCommandFromInput("a four", validationErrors);
        assertNull(commandHistory);
        assertEquals(validationErrors.getErrors().size(), 1);
        assertEquals(validationErrors.getErrors().get(0), "ERROR: For Advance commands, please enter numeric steps to advance");
    }

    @Test
    public void testAdvanceCommand() {
        CommandHistory commandHistory = commandService.generateCommandFromInput("a 4", validationErrors);
        assertNotNull(commandHistory);
        assertEquals(validationErrors.getErrors().size(), 0);
        assertEquals(commandHistory.getCommand().toString(), Command.ADVANCE.toString());
        assertEquals(commandHistory.getSteps(), 4);
    }

    @Test
    public void testRightCommand() {
        CommandHistory commandHistory = commandService.generateCommandFromInput("RIGHT", validationErrors);
        assertNotNull(commandHistory);
        assertEquals(validationErrors.getErrors().size(), 0);
        assertEquals(commandHistory.getCommand().toString(), Command.RIGHT.toString());
        assertEquals(commandHistory.getSteps(), 0);
    }

    @AfterAll
    public static void tearDown() {
        commandService = null;
    }

    @AfterEach
    public void validationErrorsTearDown() {
        validationErrors = null;
    }
}
