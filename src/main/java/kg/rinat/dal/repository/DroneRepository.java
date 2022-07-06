/*
 * Rinat Muratidinov. Do not reproduce without permission in writing.
 * Copyright (c) 2022 RM. All rights reserved.
 */

package kg.rinat.dal.repository;

import kg.rinat.dal.entity.DroneEntity;
import kg.rinat.model.schema.DroneSchema.DroneState;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Drone repository
 *
 * @author Rinat Muratidinov
 */
@Repository
public interface DroneRepository extends CrudRepository<DroneEntity, String> {

  /**
   * Find drones by state
   *
   * @param droneState State
   * @return List of drones
   */
  List<DroneEntity> findByStateIn(List<DroneState> droneState);
}
