package com.demo.authdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.authdemo.entity.Material;
import com.demo.authdemo.entity.Personel;
import com.demo.authdemo.entity.Room;
import com.demo.authdemo.service.MaterialService;
import com.demo.authdemo.service.PersonelService;

@RestController
@RequestMapping("/api/personel")
public class PersonelController {

    @Autowired
    private PersonelService personelService;

    @Autowired
    private MaterialService materialService;

    // Personel arama işlemi
    @GetMapping("/search")
    public ResponseEntity<?> searchPersonel(@RequestParam("query") String query) {
        List<Personel> personelList = personelService.searchPersonel(query);

        // Eğer personel bulunamazsa boş liste yerine bir mesaj döndürüyoruz
        if (personelList.isEmpty()) {
            return ResponseEntity.ok("Personel bulunamadı.");
        }

        return ResponseEntity.ok(personelList);
    }

    // Personel malzemelerini alma
    @GetMapping("/materials")
    public ResponseEntity<List<Material>> getMaterialsByPersonel(@RequestParam("perId") Long perId) {
        List<Material> materials = personelService.getMaterialsByPersonel(perId);
        if (materials.isEmpty()) {
            return ResponseEntity.ok().body(null); // Malzeme yoksa boş dönebiliriz
        }
        return ResponseEntity.ok(materials);
    }

    // Bulunan malzeme sayısını alma
    @GetMapping("/count-found-materials")
    public ResponseEntity<Integer> getFoundMaterialCountByPersonel(@RequestParam("perId") Long perId,
                                                                   @RequestParam("roomId") Long roomId) {
        int foundMaterials = materialService.getFoundMaterialCountByPersonelAndRoom(perId, roomId);
        return ResponseEntity.ok(foundMaterials);
    }

    // Bulunmayan malzeme sayısını alma
    @GetMapping("/count-not-found-materials")
    public ResponseEntity<Integer> getNotFoundMaterialCountByPersonel(@RequestParam("perId") Long perId,
                                                                      @RequestParam("roomId") Long roomId) {
        int notFoundMaterials = materialService.getNotFoundMaterialCountByPersonelAndRoom(perId, roomId);
        return ResponseEntity.ok(notFoundMaterials);
    }

    // Toplam malzeme sayısını alma
    @GetMapping("/count-total-materials")
    public ResponseEntity<Integer> getTotalMaterialCountByPersonelAndRoom(@RequestParam("perId") Long perId,
                                                                          @RequestParam("roomId") Long roomId) {
        int totalMaterials = materialService.getTotalMaterialCountByPersonelAndRoom(perId, roomId);
        return ResponseEntity.ok(totalMaterials);
    }

    // Farklı odalardaki malzeme sayısını alma
    @GetMapping("/materials-in-other-rooms")
    public ResponseEntity<Integer> countMaterialsInOtherRoomsByPersonel(@RequestParam("perId") Long perId,
                                                                        @RequestParam("roomId") Long roomId) {
        int materialsInOtherRooms = personelService.countMaterialsInOtherRoomsByPersonel(perId, roomId);
        return ResponseEntity.ok(materialsInOtherRooms);
    }

    // Personel'e ait odaları alma
    @GetMapping("/rooms-by-personel")
    public ResponseEntity<List<Room>> getRoomsByPersonelId(@RequestParam("perId") Long perId) {
        List<Room> rooms = materialService.findDistinctRoomsByPersonelId(perId);
        if (rooms.isEmpty()) {
            return ResponseEntity.ok().body(null); // Oda yoksa boş dönebiliriz
        }
        return ResponseEntity.ok(rooms);
    }

    // Malzeme durumunu güncelleme
    @PostMapping("/update-material-status")
    public ResponseEntity<String> updateMaterialStatusByParams(@RequestParam Long roomId,
                                                               @RequestParam Long perId,
                                                               @RequestParam Long barkodNo,
                                                               @RequestParam boolean found) {
        materialService.updateMaterialStatusByParams(roomId, perId, barkodNo, found);
        return ResponseEntity.ok("Malzeme durumu başarıyla güncellendi.");
    }
}