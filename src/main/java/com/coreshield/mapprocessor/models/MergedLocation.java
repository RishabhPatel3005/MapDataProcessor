package com.coreshield.mapprocessor.models;

import lombok.*;

/**
 * Represents a combined data structure that merges Location and Metadata.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MergedLocation {

    private String id; //Unique Identifier

    private double latitude; //Latitude Coordinate

    private double longitude; //Longitude Coordinate

    private String type; //Type of Location

    private double rating; //Average Rating

    private int reviews; //Number of reviews
}
