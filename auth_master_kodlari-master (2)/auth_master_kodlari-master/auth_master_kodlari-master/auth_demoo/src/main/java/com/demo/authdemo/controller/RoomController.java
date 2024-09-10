package com.demo.authdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.authdemo.entity.Room;
import com.demo.authdemo.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // Tüm odaları detaylarıyla birlikte döndüren endpoint
    @GetMapping("/all")
    public List<Room> getAllRooms() {
        return roomService.getAllRoomsWithDetails();  // Oda detaylarıyla birlikte döner
    }

    // Eğer sadece "/api/rooms" isteniyorsa direkt olarak tüm odaları döndürür
    @GetMapping
    public List<Room> getRooms() {
        return roomService.getAllRoomsWithDetails();  // Oda detayları olmadan döner
    }

    // Belirli bir alt lokasyona bağlı odaları döndüren endpoint
    @GetMapping("/{subId}")
    public List<Room> getRoomsBySubId(@PathVariable Long subId) {
        return roomService.getRoomsBySubId(subId);
    }

    // Oda seçimi sonrası yapılan işlemi gerçekleştiren endpoint
    @PostMapping("/select")
    public ResponseEntity<String> selectRoom(@RequestBody Room room) {
        // Oda seçildiğinde yapılacak işlemler burada belirlenebilir
        return ResponseEntity.ok("Room " + room.getOdaNum() + " selected");
    }
}