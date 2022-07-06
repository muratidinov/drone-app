/*
 * Rinat Muratidinov. Do not reproduce without permission in writing.
 * Copyright (c) 2022 RM. All rights reserved.
 */

package kg.rinat.dal.repository;

import kg.rinat.dal.entity.MedicationEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Medication repository
 *
 * @author Rinat Muratidinov
 */
@Repository
public interface MedicationRepository extends CrudRepository<MedicationEntity, Long> {

  /**
   * Find medications by drone serial number
   *
   * @param droneSerialNumber Drone serial number
   * @return Medications
   */
  List<MedicationEntity> findByDroneSerialNumber(String droneSerialNumber);
}
