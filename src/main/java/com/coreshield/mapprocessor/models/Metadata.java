package com.coreshield.mapprocessor.models;

import lombok.*;

/**
 * Represents metadata related to a location, including type, rating, and reviews.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {

    private String id; //Unique Identifier

    private String type; //Type of location i.e restaurant,hotel,cafe

    private double rating; //Average rating of the location

    private int reviews; //Number of reviews of the location
}
