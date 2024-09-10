package com.demo.authdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.authdemo.dto.FoundMaterialDto;
import com.demo.authdemo.entity.FoundMaterial;
import com.demo.authdemo.service.FoundMaterialService;
import com.demo.authdemo.service.MaterialService;
import com.demo.authdemo.service.PersonelService;
import com.demo.authdemo.service.RoomService;

@RestController
@RequestMapping("/api/found-materials")
public class FoundMaterialController {

    @Autowired
    private FoundMaterialService foundMaterialService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private PersonelService personelService;

    @Autowired
    private RoomService roomService;

    @PostMapping("/save")
    public ResponseEntity<FoundMaterial> saveFoundMaterial(@RequestBody FoundMaterial foundMaterial) {
        System.out.println("gelen material" + foundMaterial.toString());
        FoundMaterial savedMaterial = foundMaterialService.saveFoundMaterial(foundMaterial);
        return ResponseEntity.ok(savedMaterial);
    }

    @PostMapping("/save-all")
    public ResponseEntity<List<FoundMaterial>> saveAllFoundMaterials(@RequestBody List<FoundMaterial> foundMaterials) {
        List<FoundMaterial> savedMaterials = foundMaterialService.saveAllFoundMaterials(foundMaterials);
        return ResponseEntity.ok(savedMaterials);
    }

    @PostMapping("/save-for-year")
    public ResponseEntity<FoundMaterial> saveFoundMaterialForYear(@RequestBody FoundMaterialDto foundMaterialDto) {
        FoundMaterial foundMaterial = new FoundMaterial();
        foundMaterial.setMaterial(materialService.findById(foundMaterialDto.getMaterialId()));
        foundMaterial.setPersonel(personelService.findById(foundMaterialDto.getPersonelId()));
        foundMaterial.setRoom(roomService.findById(foundMaterialDto.getRoomId()));
        foundMaterial.setSicilNo(foundMaterialDto.getSicilNo());
        foundMaterial.setFoundDate(foundMaterialDto.getYear());
        FoundMaterial savedMaterial = foundMaterialService.saveFoundMaterial(foundMaterial);
        return ResponseEntity.ok(savedMaterial);
    }
}