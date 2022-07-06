/*
 * Rinat Muratidinov. Do not reproduce without permission in writing.
 * Copyright (c) 2022 RM. All rights reserved.
 */

package kg.rinat.dal.entity;

import static kg.rinat.model.schema.DroneSchema.DroneModel;
import static kg.rinat.model.schema.DroneSchema.DroneState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Drone entity
 *
 * @author Rinat Muratidinov
 */
@Entity
@Table(name = "drone")
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class DroneEntity {

  /** Drones serial number */
  @Id
  @Column(name = "serial_number")
  @EqualsAndHashCode.Include
  private String serialNumber;

  /** Model */
  @Column(name = "model")
  @Enumerated(EnumType.STRING)
  private DroneModel model;

  /** Max allowed weight */
  @Column(name = "allowed_weight")
  private Integer allowedWeight;

  /** Current weight of loaded medications */
  @Column(name = "current_weight")
  private Integer currentWeight;

  /** Drones battery level in percent */
  @Column(name = "battery_level")
  private Integer batteryLevel;

  /** State */
  @Column(name = "state")
  @Enumerated(EnumType.STRING)
  private DroneState state;

  /** List of loaded medications */
  @OneToMany(mappedBy = "drone", cascade = CascadeType.PERSIST)
  @ToString.Exclude
  private List<MedicationEntity> medications;
}
