package com.demo.authdemo.repository;

import com.demo.authdemo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE r.subLocation.id = :subId")
    List<Room> findBySubId(Long subId);
}