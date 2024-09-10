package com.demo.authdemo.service;

import com.demo.authdemo.entity.Location;
import com.demo.authdemo.entity.SubLocation;
import com.demo.authdemo.repository.LocationRepository;
import com.demo.authdemo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;

    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public List<SubLocation> getAllSubLocations(Long locationId) {
        return locationRepository.getAllSubLocationByLocationId(locationId);
    }

    public Long getLocationIdByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getLocation()
                .getId();
    }
}