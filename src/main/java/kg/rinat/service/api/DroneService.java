/*
 * Rinat Muratidinov. Do not reproduce without permission in writing.
 * Copyright (c) 2022 RM. All rights reserved.
 */

package kg.rinat.service.api;

import kg.rinat.model.schema.DroneSchema;
import kg.rinat.model.schema.MedicationSchema;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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
   * @param medicationRequestDto Medications data
   * @param images Images of medication
   * @return Http status
   */
  ResponseEntity<Void> loadMedications(
      String droneSerialNumber,
      MedicationSchema.MedicationRequestDto medicationRequestDto,
      List<MultipartFile> images);

  /**
   * Getting medications of drone
   *
   * @param droneSerialNumber Drones serial number
   * @param withImages Need to fill images in response?
   * @return Medications list
   */
  List<MedicationSchema.MedicationResponseDto> getMedicationList(
      String droneSerialNumber, boolean withImages);

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
