package com.demo.authdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.authdemo.entity.FoundMaterial;

public interface FoundMaterialRepository extends JpaRepository<FoundMaterial, Long> {
    // Malzeme zaten kaydedildiyse true döner
    boolean existsByMaterialMatId(Long matId);
}