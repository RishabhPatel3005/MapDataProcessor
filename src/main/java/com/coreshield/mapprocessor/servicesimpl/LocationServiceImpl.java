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

@Service
public class LocationServiceImpl implements LocationService {

    private List<Location> locations;
    private List<Metadata> metadataList;
    private List<MergedLocation> mergedLocations;

    public LocationServiceImpl() throws IOException {
        loadJsonData();
        mergeData();
    }

    @Override
    public void loadJsonData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        locations = objectMapper.readValue(Paths.get("src/main/resources/locations.json").toFile(),new TypeReference<List<Location>>() {});
        metadataList = objectMapper.readValue(Paths.get("src/main/resources/metadata.json").toFile(),new TypeReference<List<Metadata>>() {});
    }

    @Override
    public void mergeData() {
        Map<String,Metadata> metadataMap = metadataList.stream().collect(Collectors.toMap(Metadata::getId,m -> m));
        mergedLocations = locations.stream().filter(loc -> metadataMap.containsKey(loc.getId()))
                .map(loc -> {
                    Metadata meta = metadataMap.get(loc.getId());
                    return new MergedLocation(loc.getId(),loc.getLatitude(),loc.getLongitude(),meta.getType(),meta.getRating(), meta.getReviews());
                }).collect(Collectors.toList());
    }

    @Override
    public Map<String, Long> countByType() {
        return mergedLocations.stream().collect(Collectors.groupingBy(MergedLocation::getType,Collectors.counting()));
    }

    @Override
    public Map<String, Double> averageRatingType() {
        return mergedLocations.stream()
                .collect(Collectors.groupingBy(MergedLocation::getType,Collectors.averagingDouble(MergedLocation::getRating)));
    }

    @Override
    public MergedLocation highestReviewedLocation() {
        return mergedLocations.stream()
                .max(Comparator.comparingInt(MergedLocation::getReviews)).orElse(null);
    }

    @Override
    public List<Location> incompleteData() {
        Set<String> metadataIds = metadataList.stream().map(Metadata::getId).collect(Collectors.toSet());
        return locations.stream().filter(loc -> !metadataIds.contains(loc.getId())).collect(Collectors.toList());
    }
}
