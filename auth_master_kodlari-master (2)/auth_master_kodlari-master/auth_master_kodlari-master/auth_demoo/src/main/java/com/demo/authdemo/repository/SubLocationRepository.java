package com.demo.authdemo.repository;
import com.demo.authdemo.entity.SubLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;


public interface SubLocationRepository extends JpaRepository<SubLocation, Long> {

    @Query("SELECT s FROM SubLocation s WHERE LOWER(s.name) LIKE LOWER(CONCAT(:query, '%')) AND s.location.id = :locationId")
    List<SubLocation> findByNameStartingWithAndLocationId(String query, Long locationId);
}