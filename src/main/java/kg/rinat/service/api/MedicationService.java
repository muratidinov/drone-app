/*
 * Rinat Muratidinov. Do not reproduce without permission in writing.
 * Copyright (c) 2022 RM. All rights reserved.
 */

package kg.rinat.service.api;

import kg.rinat.model.schema.MedicationSchema;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Medications service
 *
 * @author Rinat Muratidinov
 */
public interface MedicationService {

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
   * Load medication images
   *
   * @param medicationId Medication id
   * @param images Images
   * @return Http status
   */
  ResponseEntity<Void> loadMedicationImages(Long medicationId, List<MultipartFile> images);
}
