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

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/count-by-type")
    public Map<String,Long> countByType(){
        return locationService.countByType();
    }

    @GetMapping("/average-rating")
    public Map<String,Double> averageRatingByType(){
        return locationService.averageRatingType();
    }

    @GetMapping("highest-reviewed")
    public MergedLocation highestReviewedLocation(){
        return locationService.highestReviewedLocation();
    }

    @GetMapping("/incomplete-data")
    public List<Location> incompleteData(){
        return locationService.incompleteData();
    }
}
