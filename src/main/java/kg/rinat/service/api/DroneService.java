/*
 * Rinat Muratidinov. Do not reproduce without permission in writing.
 * Copyright (c) 2022 RM. All rights reserved.
 */

package kg.rinat.service.api;

import kg.rinat.model.schema.DroneSchema;
import kg.rinat.model.schema.MedicationSchema;

import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Drones service
 *
 * @author Rinat Muratidinov
 */
public interface DroneService {

  /**
   * Registering drone
   *
   * @param droneRequestDto Drone request data
   * @return Serial number of registered drone
   */
  String registerDrone(DroneSchema.DroneRequestDto droneRequestDto);

  /**
   * Load medications to drone
   *
   * @param droneSerialNumber Drones serial number
   * @param medicationRequestDtoList Medications data
   * @return Http status
   */
  ResponseEntity<Void> loadMedications(
      String droneSerialNumber,
      List<MedicationSchema.MedicationRequestDto> medicationRequestDtoList);

  /**
   * Getting available drones for loading medications
   *
   * @return Drones list
   */
  List<DroneSchema.DroneResponseDto> getAvailableDrones();

  /**
   * Getting battery level of drone
   *
   * @param droneSerialNumber Drones serial number
   * @return Battery level in percent
   */
  Integer getBatteryLevel(String droneSerialNumber);
}
