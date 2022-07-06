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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Medication image entity
 *
 * @author Rinat Muratidinov
 */
@Entity
@Table(name = "medication_image")
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class MedicationImageEntity {

  /** Id */
  @Id
  @Column(name = "medication_image_id")
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long medicationId;

  /** The medicine to which the picture belongs */
  @JoinColumn(name = "medication_id")
  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private MedicationEntity medication;

  /** Encoded image data */
  @Lob
  @Column(name = "image")
  private byte[] image;
}
