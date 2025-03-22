package com.coreshield.mapprocessor.servicesimpl;

import com.coreshield.mapprocessor.models.Location;
import com.coreshield.mapprocessor.models.MergedLocation;
import com.coreshield.mapprocessor.models.Metadata;
import com.coreshield.mapprocessor.services.LocationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of LocationService that processes and analyzes location data.
 */

@Service
public class LocationServiceImpl implements LocationService {

    private List<Location> locations;
    private List<Metadata> metadataList;
    private List<MergedLocation> mergedLocations;

    /**
     * Constructor that loads JSON data and merges it.
     */
    public LocationServiceImpl() throws IOException {
        loadJsonData();
        mergeData();
    }

    /**
     * Loads location and metadata from JSON files.
     */
    @Override
    public void loadJsonData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Read location data from JSON file
        locations = objectMapper.readValue(Paths.get("src/main/resources/locations.json").toFile(),new TypeReference<List<Location>>() {});

        // Read metadata from JSON file
        metadataList = objectMapper.readValue(Paths.get("src/main/resources/metadata.json").toFile(),new TypeReference<List<Metadata>>() {});
    }

    /**
     * Merges location and metadata based on matching ID.
     */
    @Override
    public void mergeData() {
        Map<String,Metadata> metadataMap = metadataList.stream().collect(Collectors.toMap(Metadata::getId,m -> m));
        mergedLocations = locations.stream().filter(loc -> metadataMap.containsKey(loc.getId()))
                .map(loc -> {
                    Metadata meta = metadataMap.get(loc.getId());
                    return new MergedLocation(loc.getId(),loc.getLatitude(),loc.getLongitude(),meta.getType(),meta.getRating(), meta.getReviews());
                }).collect(Collectors.toList());
    }

   //Counts how many valid locations exist per type (e.g., restaurants, hotels).
    @Override
    public Map<String, Long> countByType() {
        return mergedLocations.stream().collect(Collectors.groupingBy(MergedLocation::getType,Collectors.counting()));
    }

    // Calculates the average rating per type, considering only valid entries.
    @Override
    public Map<String, Double> averageRatingType() {
        return mergedLocations.stream()
                .collect(Collectors.groupingBy(MergedLocation::getType,Collectors.averagingDouble(MergedLocation::getRating)));
    }

    //Finds the location with the highest number of reviews.
    @Override
    public MergedLocation highestReviewedLocation() {
        return mergedLocations.stream()
                .max(Comparator.comparingInt(MergedLocation::getReviews)).orElse(null);
    }

    //Identifies locations with incomplete data (locations missing metadata).
    @Override
    public List<Location> incompleteData() {
        Set<String> metadataIds = metadataList.stream().map(Metadata::getId).collect(Collectors.toSet());
        return locations.stream().filter(loc -> !metadataIds.contains(loc.getId())).collect(Collectors.toList());
    }
}
