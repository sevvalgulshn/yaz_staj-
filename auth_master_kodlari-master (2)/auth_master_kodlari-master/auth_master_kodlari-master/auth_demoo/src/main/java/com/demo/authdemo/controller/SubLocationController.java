package com.demo.authdemo.controller;
import com.demo.authdemo.entity.SubLocation;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.demo.authdemo.repository.SubLocationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/sublocations")
public class SubLocationController {

    @Autowired
    private SubLocationRepository subLocationRepository;

    @GetMapping
    public List<SubLocation> getAllLocation() {
        return subLocationRepository.findAll();
    }

    @GetMapping("/search")
    public List<SubLocation> searchSubLocations(@RequestParam("query") String query, @RequestParam("locationId") Long locationId) {
        return subLocationRepository.findByNameStartingWithAndLocationId(query, locationId);
    }
}