package com.site.construction.data;

import com.site.construction.constants.Orientation;

//Bulldozer object, has location and orientation
public class Bulldozer {
    private Location currentLocation;
    private Orientation orientation;

    public Bulldozer(Location currentLocation, Orientation orientation) {
        this.currentLocation = currentLocation;
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public Location getCurrentLocation() {
        return this.currentLocation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }
}
