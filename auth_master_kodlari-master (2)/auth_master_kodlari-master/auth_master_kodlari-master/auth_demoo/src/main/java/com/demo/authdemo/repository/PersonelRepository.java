package com.demo.authdemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.authdemo.entity.Personel;
import com.demo.authdemo.entity.Room;

public interface PersonelRepository extends JpaRepository<Personel, Long> {
    List<Personel> findBySicilNoOrAdSoyadContainingIgnoreCase(String sicilNo, String adSoyad);
    @Query("SELECT p FROM Personel p LEFT JOIN FETCH p.room WHERE p.sicilNo LIKE %:query% OR LOWER(p.adSoyad) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Personel> searchPersonelWithRooms(@Param("query") String query);

    @Query("SELECT p.room FROM Personel p WHERE p.per_id = :perId")
    List<Room> findRoomsByPersonelId(@Param("perId") Long perId);


}