package com.coreshield.mapprocessor.services;

import com.coreshield.mapprocessor.models.Location;
import com.coreshield.mapprocessor.models.MergedLocation;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface LocationService {

    void loadJsonData() throws IOException;

    void mergeData();

    Map<String,Long> countByType();

    Map<String,Double> averageRatingType();

    MergedLocation highestReviewedLocation();

    List<Location> incompleteData();
}
