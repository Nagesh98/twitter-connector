package org.mule.connectors.api;

import org.mule.runtime.extension.api.annotation.param.Parameter;

public class Location {

    @Parameter
    private double longitude;

    @Parameter
    private double latitude;

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
