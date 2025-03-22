package com.coreshield.mapprocessor.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {

    private String id;

    private String type;

    private double rating;

    private int reviews;
}
