package com.coreshield.mapprocessor.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MergedLocation {

    private String id;

    private double latitude;

    private double longitude;

    private String type;

    private double rating;

    private int reviews;
}
