package com.demo.authdemo.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.authdemo.entity.Material;
import com.demo.authdemo.entity.Room;
import com.demo.authdemo.repository.MaterialRepository;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;





    public int getTotalMaterialCount(Long id) {
        return materialRepository.countMaterialsByRoomId(id);
    }

    public int getFoundMaterialCount(Long id) {
        return materialRepository.countFoundMaterialsByRoomId(id);
    }

    public int getMaterialCountInOtherLocations(Long id) {
        return materialRepository.countMaterialsInOtherLocations(id);
    }

    public void updateMaterialStatus(Long barkodNo, boolean found) {
        Optional<Material> materialOpt = materialRepository.findByBarkodNo(barkodNo);
        if (materialOpt.isPresent()) {
            Material material = materialOpt.get();
            material.setBulduMu(found);
            materialRepository.save(material);
        } else {
            throw new RuntimeException("Material not found with barcode: " + barkodNo);
        }
    }

    public Material getMaterialDetailsByBarkodNo(Long barkodNo) {
        return materialRepository.findByBarkodNoWithDetails(barkodNo)
                .orElseThrow(() -> new RuntimeException("Material not found with barcode: " + barkodNo));
    }

    public int getFoundMaterialCountByPersonelAndRoom(Long perId, Long roomId) {
        return materialRepository.countFoundMaterialsByPersonelAndRoom(perId, roomId);
    }

    public int getNotFoundMaterialCountByPersonelAndRoom(Long perId, Long roomId) {
        return materialRepository.countNotFoundMaterialsByPersonelAndRoom(perId, roomId);
    }

    public int getTotalMaterialCountByPersonelAndRoom(Long perId, Long roomId) {
        return materialRepository.countTotalMaterialsByPersonelAndRoom(perId, roomId);
    }

    public int countMaterialsInOtherRoomsByPersonel(Long perId, Long roomId) {
        return materialRepository.countMaterialsInOtherRoomsByPersonel(perId, roomId);
    }

    public List<Room> findDistinctRoomsByPersonelId(Long perId) {
        return materialRepository.findDistinctRoomsByPersonelId(perId);
    }

    public void updateMaterialStatusByParams(Long roomId, Long perId, Long barkodNo, boolean found) {
        Optional<Material> materialOpt = materialRepository.findByRoomIdAndPersonelIdAndBarkodNo(roomId, perId, barkodNo);
        if (materialOpt.isPresent()) {
            Material material = materialOpt.get();
            material.setBulduMu(found);
            materialRepository.save(material);
        } else {
            throw new RuntimeException("Material not found with roomId: " + roomId + ", perId: " + perId + ", barkodNo: " + barkodNo);
        }
    }

    public int getTotalMaterialCountByLocation(Long locationId) {
        return materialRepository.countMaterialsByLocationId(locationId);
    }

    public Material findById(Long materialId) {

        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }



}