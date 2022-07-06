/*
 * Rinat Muratidinov. Do not reproduce without permission in writing.
 * Copyright (c) 2022 RM. All rights reserved.
 */

package kg.rinat.dal.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Medication entity
 *
 * @author Rinat Muratidinov
 */
@Entity
@Table(name = "medication")
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class MedicationEntity {

  /** Id */
  @Id
  @Column(name = "medication_id")
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long medicationId;

  /** Drone, that medication loaded on */
  @ManyToOne
  @JoinColumn(name = "drone_serial_number")
  @ToString.Exclude
  private DroneEntity drone;

  /** Name */
  @Column(name = "name")
  private String name;

  /** Weight */
  @Column(name = "weight")
  private Integer weight;

  /** Code */
  @Column(name = "code")
  private String code;

  /** Images of medication */
  @OneToMany(mappedBy = "medication", cascade = CascadeType.PERSIST)
  @ToString.Exclude
  private List<MedicationImageEntity> images;
}
