package com.coreshield.mapprocessor.services;

import com.coreshield.mapprocessor.models.Location;
import com.coreshield.mapprocessor.models.MergedLocation;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Service interface for handling location data processing.
 */

public interface LocationService {

    void loadJsonData() throws IOException; // Loads the JSON Data from the file provided in the resources folder

    void mergeData(); // Merges the location and metadata into one

    Map<String,Long> countByType(); // Calculate valid locations per type

    Map<String,Double> averageRatingType(); // Calculate average rating per type

    MergedLocation highestReviewedLocation(); // Find the location with the highest reviews

    List<Location> incompleteData(); // Identify locations with incomplete data
}
