package com.site.construction.service;

import com.site.construction.constants.Command;
import com.site.construction.data.Location;
import com.site.construction.constants.Orientation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleServiceTest {
    private static VehicleService vehicleService;

    @BeforeAll
    public static void setUp() {
        vehicleService = new BulldozerServiceImpl();
    }

    @Test
    public void testIsOutOfBounds() {
        assertTrue(vehicleService.isOutOfBounds(new Location(0, 10), 10, 10));
    }

    @Test
    public void testIsNotOutOfBounds() {
        assertFalse(vehicleService.isOutOfBounds(new Location(0, 9), 10, 10));
    }

    @Test
    public void testTurnRightFromDown() {
        assertEquals(vehicleService.changeOrientation(Orientation.DOWN, Command.RIGHT).toString(), Command.LEFT.toString());
    }

    @Test
    public void testTurnLeftFromUp() {
        assertEquals(vehicleService.changeOrientation(Orientation.UP, Command.LEFT).toString(), Command.LEFT.toString());
    }

    @Test
    public void testTakeRightStep() {
        assertEquals(vehicleService.takeStep(Orientation.RIGHT, new Location(0, 1)).getRow(), 0);
        assertEquals(vehicleService.takeStep(Orientation.RIGHT, new Location(0, 1)).getColumn(), 2);
    }

    @Test
    public void testTakeDownStep() {
        assertEquals(vehicleService.takeStep(Orientation.DOWN, new Location(0, 1)).getRow(), 1);
        assertEquals(vehicleService.takeStep(Orientation.DOWN, new Location(0, 1)).getColumn(), 1);
    }

    @AfterAll
    public static void tearDown() {
        vehicleService = null;
    }
}
