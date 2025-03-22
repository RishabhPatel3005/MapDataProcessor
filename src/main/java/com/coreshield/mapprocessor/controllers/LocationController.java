package com.coreshield.mapprocessor.controllers;

import com.coreshield.mapprocessor.models.Location;
import com.coreshield.mapprocessor.models.MergedLocation;
import com.coreshield.mapprocessor.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for handling location data requests.
 */

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    /**
     * Get count of locations by type.
     */
    @GetMapping("/count-by-type")
    public Map<String,Long> countByType(){
        return locationService.countByType();
    }

    /**
     * Get average rating per type.
     */
    @GetMapping("/average-rating")
    public Map<String,Double> averageRatingByType(){
        return locationService.averageRatingType();
    }

    /**
     * Get the location with the highest number of reviews.
     */
    @GetMapping("highest-reviewed")
    public MergedLocation highestReviewedLocation(){
        return locationService.highestReviewedLocation();
    }

    /**
     * Get a list of locations with incomplete data.
     */
    @GetMapping("/incomplete-data")
    public List<Location> incompleteData(){
        return locationService.incompleteData();
    }
}
