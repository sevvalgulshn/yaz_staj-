package com.demo.authdemo.repository;

import com.demo.authdemo.entity.Material;
import com.demo.authdemo.entity.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    @Query("SELECT COUNT(m) FROM Material m WHERE m.room.id = :id")
    int countMaterialsByRoomId(@Param("id") Long id);

    @Query("SELECT COUNT(m) FROM Material m WHERE m.room.id = :id AND m.bulduMu = true")
    int countFoundMaterialsByRoomId(@Param("id") Long id);

    @Query("SELECT COUNT(m) FROM Material m WHERE m.room.id != :id")
    int countMaterialsInOtherLocations(@Param("id") Long id);

    @Query("SELECT m FROM Material m WHERE m.barkodNo = :barkodNo")
    Optional<Material> findByBarkodNo(@Param("barkodNo") Long barkodNo);

    @Query("SELECT m FROM Material m JOIN FETCH m.room r JOIN FETCH r.subLocation s WHERE m.barkodNo = :barkodNo")
    Optional<Material> findByBarkodNoWithDetails(@Param("barkodNo") Long barkodNo);

    @Query("SELECT COUNT(m) FROM Material m WHERE m.room.id = :roomId AND m.personel.per_id = :perId AND m.bulduMu = true")
    int countFoundMaterialsByPersonelAndRoom(@Param("perId") Long perId, @Param("roomId") Long roomId);

    @Query("SELECT COUNT(m) FROM Material m WHERE m.room.id = :roomId AND m.personel.per_id = :perId AND m.bulduMu = false")
    int countNotFoundMaterialsByPersonelAndRoom(@Param("perId") Long perId, @Param("roomId") Long roomId);

    @Query("SELECT COUNT(m) FROM Material m WHERE m.room.id = :roomId AND m.personel.per_id = :perId")
    int countTotalMaterialsByPersonelAndRoom(@Param("perId") Long perId, @Param("roomId") Long roomId);

    @Query("SELECT COUNT(m) FROM Material m WHERE m.room.id != :roomId AND m.personel.per_id = :perId")
    int countMaterialsInOtherRoomsByPersonel(@Param("perId") Long perId, @Param("roomId") Long roomId);

    @Query("SELECT m FROM Material m WHERE m.personel.per_id = :perId")
    List<Material> findByPersonelPerId(@Param("perId") Long perId);

    @Query("SELECT DISTINCT r FROM Material m JOIN m.room r WHERE m.personel.per_id = :perId")
    List<Room> findDistinctRoomsByPersonelId(@Param("perId") Long perId);

    @Query("SELECT m FROM Material m WHERE m.room.id = :roomId AND m.personel.per_id = :perId AND m.barkodNo = :barkodNo")
    Optional<Material> findByRoomIdAndPersonelIdAndBarkodNo(@Param("roomId") Long roomId,
                                                            @Param("perId") Long perId,
                                                            @Param("barkodNo") Long barkodNo);

    @Query("SELECT COUNT(m) FROM Material m WHERE m.room.subLocation.location.id = :locationId")
    int countMaterialsByLocationId(@Param("locationId") Long locationId);

}