package com.coreshield.mapprocessor.models;

import lombok.*;

/**
 * Represents a geographical location with latitude and longitude.
 * */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private String id; //Unique Identifier for location

    private double latitude; //Latitude Coordinate

    private double longitude; //Longitude Coordinate
}
