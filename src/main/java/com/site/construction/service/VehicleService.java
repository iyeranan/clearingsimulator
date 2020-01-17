package com.site.construction.service;

import com.site.construction.constants.Command;
import com.site.construction.data.Location;
import com.site.construction.constants.Orientation;

public interface VehicleService {
    boolean isOutOfBounds(Location currentLocation, int rows, int columns);

    Orientation changeOrientation(Orientation currentOrientation, Command changeBy);

    Location takeStep(Orientation currentOrientation, Location currentLocation);
}
