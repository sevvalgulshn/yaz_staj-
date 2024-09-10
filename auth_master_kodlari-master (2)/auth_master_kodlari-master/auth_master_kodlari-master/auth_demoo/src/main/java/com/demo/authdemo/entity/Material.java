package com.demo.authdemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "materials")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matId;

    private Long barkodNo;

    private Boolean bulduMu = false;

    private String model;
    private String marka;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "per_id", referencedColumnName = "per_id")
    private Personel personel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;
}