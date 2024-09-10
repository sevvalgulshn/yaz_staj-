package com.demo.authdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.authdemo.entity.Room;
import com.demo.authdemo.repository.RoomRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // Tüm odaları getiren metod
    public List<Room> getAllRoomsWithDetails() {
        return roomRepository.findAll();
    }

    public List<Room> getRoomsBySubId(Long subId) {
        return roomRepository.findBySubId(subId);
    }

    public Room findById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
    }
}