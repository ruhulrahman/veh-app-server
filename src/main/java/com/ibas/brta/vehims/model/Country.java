package com.ibas.brta.vehims.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "c_countries")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(callSuper = false)

@AttributeOverride(name = "id", column = @Column(name = "designation_id"))

public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_en", nullable = false, unique = true)
    private String nameEn;

    @Column(name = "name_bn", nullable = false, unique = true)
    private String nameBn;
}
