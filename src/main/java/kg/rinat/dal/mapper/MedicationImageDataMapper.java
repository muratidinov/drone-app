/*
 * Rinat Muratidinov. Do not reproduce without permission in writing.
 * Copyright (c) 2022 RM. All rights reserved.
 */

package kg.rinat.dal.mapper;

import kg.rinat.dal.entity.MedicationEntity;
import kg.rinat.dal.entity.MedicationImageEntity;
import kg.rinat.exception.DroneServiceException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Medication image data mapper
 *
 * @author Rinat Muratidinov
 */
@Slf4j
@Component
public class MedicationImageDataMapper {

  /**
   * Build DB entity from multipartfile
   *
   * @param multipartFile Image encoded data
   * @param medicationEntity Medication entity
   * @return Entity of medication image
   */
  public MedicationImageEntity toMedicationImageEntity(
      MultipartFile multipartFile, MedicationEntity medicationEntity) {

    if (multipartFile == null || multipartFile.isEmpty() || medicationEntity == null) {
      return null;
    }

    try {
      return MedicationImageEntity.builder()
          .image(multipartFile.getBytes())
          .medication(medicationEntity)
          .build();
    } catch (IOException e) {
      String message = "error during getting image from multiPartFile";

      log.warn(message, e);
      throw new DroneServiceException(message);
    }
  }
}
