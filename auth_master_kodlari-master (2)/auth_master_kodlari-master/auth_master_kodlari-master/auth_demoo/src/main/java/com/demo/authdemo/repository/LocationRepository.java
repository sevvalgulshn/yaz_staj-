package com.demo.authdemo.repository;

import com.demo.authdemo.entity.Location;
import com.demo.authdemo.entity.SubLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("SELECT s FROM SubLocation s WHERE s.location.id = ?1")
    List<SubLocation> getAllSubLocationByLocationId(Long locationId);
}