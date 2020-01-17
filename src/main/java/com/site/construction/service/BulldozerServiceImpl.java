package com.site.construction.service;

import com.site.construction.constants.Command;
import com.site.construction.data.Location;
import com.site.construction.constants.Orientation;

import java.util.HashMap;
import java.util.Map;

//service executes bulldozer movement across different sites in the site map
public class BulldozerServiceImpl implements VehicleService {
    private static final Map<Orientation, int[]> incrementMap = new HashMap<>();

    static {
        incrementMap.put(Orientation.RIGHT, new int[]{0, 1});
        incrementMap.put(Orientation.LEFT, new int[]{0, -1});
        incrementMap.put(Orientation.UP, new int[]{-1, 0});
        incrementMap.put(Orientation.DOWN, new int[]{1, 0});
    }

    public boolean isOutOfBounds(Location currentLocation, int rows, int columns) {
        return (currentLocation.getRow() < 0 || currentLocation.getColumn() < 0 || currentLocation.getRow() >= rows ||
                currentLocation.getColumn() >= columns);
    }

    public Orientation changeOrientation(Orientation currentOrientation, Command changeBy) {
        Orientation[] orientations = Orientation.values();
        int newOrdinal = 0;
        switch (changeBy) {
            case LEFT:
                newOrdinal = (currentOrientation.ordinal() - 1 + orientations.length) % orientations.length;
                break;
            case RIGHT:
                newOrdinal = (currentOrientation.ordinal() + 1) % orientations.length;
                break;
            default:
                return currentOrientation;
        }
        return orientations[newOrdinal];
    }

    public Location takeStep(Orientation currentOrientation, Location currentLocation) {
        int[] incrementBy = incrementMap.get(currentOrientation);
        return new Location(currentLocation.getRow() + incrementBy[0], currentLocation.getColumn() + incrementBy[1]);
    }
}
