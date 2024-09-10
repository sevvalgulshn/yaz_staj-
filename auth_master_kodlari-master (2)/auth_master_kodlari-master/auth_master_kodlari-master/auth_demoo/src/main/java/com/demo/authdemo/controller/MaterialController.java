package com.demo.authdemo.controller;

import com.demo.authdemo.entity.Material;
import com.demo.authdemo.service.MaterialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/total/{roomId}")
    public ResponseEntity<Integer> getTotalMaterialCount(@PathVariable Long roomId) {
        int totalMaterials = materialService.getTotalMaterialCount(roomId);
        return ResponseEntity.ok(totalMaterials);
    }

    @GetMapping("/found/{roomId}")
    public ResponseEntity<Integer> getFoundMaterialCount(@PathVariable Long roomId) {
        int foundMaterials = materialService.getFoundMaterialCount(roomId);
        return ResponseEntity.ok(foundMaterials);
    }

    @GetMapping("/other-locations/{roomId}")
    public ResponseEntity<Integer> getMaterialCountInOtherLocations(@PathVariable Long roomId) {
        int materialsInOtherLocations = materialService.getMaterialCountInOtherLocations(roomId);
        return ResponseEntity.ok(materialsInOtherLocations);
    }

    @PostMapping("/update-status")
    public ResponseEntity<String> updateMaterialStatus(@RequestParam Long barkodNo, @RequestParam boolean found) {
        materialService.updateMaterialStatus(barkodNo, found);
        return ResponseEntity.ok("Material status updated");
    }

    @GetMapping("/details/{barkodNo}")
    public ResponseEntity<Material> getMaterialDetails(@PathVariable Long barkodNo) {
        Material material = materialService.getMaterialDetailsByBarkodNo(barkodNo);
        return ResponseEntity.ok(material);
    }

    @GetMapping("/found/{perId}/{roomId}")
    public ResponseEntity<Integer> getFoundMaterialCountByPersonelAndRoom(@PathVariable Long perId, @PathVariable Long roomId) {
        int foundMaterials = materialService.getFoundMaterialCountByPersonelAndRoom(perId, roomId);
        return ResponseEntity.ok(foundMaterials);
    }

    @GetMapping("/not-found/{perId}/{roomId}")
    public ResponseEntity<Integer> getNotFoundMaterialCountByPersonelAndRoom(@PathVariable Long perId, @PathVariable Long roomId) {
        int notFoundMaterials = materialService.getNotFoundMaterialCountByPersonelAndRoom(perId, roomId);
        return ResponseEntity.ok(notFoundMaterials);
    }

    @GetMapping("/total/{perId}/{roomId}")
    public ResponseEntity<Integer> getTotalMaterialCountByPersonelAndRoom(@PathVariable Long perId, @PathVariable Long roomId) {
        int totalMaterials = materialService.getTotalMaterialCountByPersonelAndRoom(perId, roomId);
        return ResponseEntity.ok(totalMaterials);
    }

    @GetMapping("/other-locations/{perId}/{roomId}")
    public ResponseEntity<Integer> countMaterialsInOtherRoomsByPersonel(@PathVariable Long perId, @PathVariable Long roomId) {
        int materialsInOtherRooms = materialService.countMaterialsInOtherRoomsByPersonel(perId, roomId);
        return ResponseEntity.ok(materialsInOtherRooms);
    }

    @GetMapping("/location/{locationId}/total")
    public ResponseEntity<Integer> getTotalMaterialCountByLocation(@PathVariable Long locationId) {
        int totalMaterials = materialService.getTotalMaterialCountByLocation(locationId);
        return ResponseEntity.ok(totalMaterials);
    }



}