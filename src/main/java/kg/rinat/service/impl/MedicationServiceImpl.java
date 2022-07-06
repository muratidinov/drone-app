/*
 * Rinat Muratidinov. Do not reproduce without permission in writing.
 * Copyright (c) 2022 RM. All rights reserved.
 */

package kg.rinat.service.impl;

import kg.rinat.dal.entity.MedicationEntity;
import kg.rinat.dal.mapper.MedicationDataMapper;
import kg.rinat.dal.mapper.MedicationImageDataMapper;
import kg.rinat.dal.repository.MedicationImageRepository;
import kg.rinat.dal.repository.MedicationRepository;
import kg.rinat.exception.DroneServiceException;
import kg.rinat.model.schema.MedicationSchema;
import kg.rinat.service.api.MedicationService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Medications service
 *
 * @author Rinat Muratidinov
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {

  /** Medication repository */
  private final MedicationRepository medicationRepository;

  /** Medication image repository */
  private final MedicationImageRepository medicationImageRepository;

  /** Medication data mapper */
  private final MedicationDataMapper medicationDataMapper;

  /** Medication image data mapper */
  private final MedicationImageDataMapper medicationImageDataMapper;

  /**
   * Getting medications of drone
   *
   * @param droneSerialNumber Drones serial number
   * @param withImages Need to fill images in response?
   * @return Medications list
   */
  @Override
  @Transactional(readOnly = true)
  public List<MedicationSchema.MedicationResponseDto> getMedicationList(
      String droneSerialNumber, boolean withImages) {
    log.debug("droneSerialNumber: {}, withImages: {}", droneSerialNumber, withImages);

    return medicationRepository.findByDroneSerialNumber(droneSerialNumber).stream()
        .map(
            medicationEntity ->
                medicationDataMapper.toMedicationResponseDto(medicationEntity, withImages))
        .collect(Collectors.toList());
  }

  /**
   * Load medication images
   *
   * @param medicationId Medication id
   * @param images Images
   * @return Http status
   */
  @Override
  @Transactional
  public ResponseEntity<Void> loadMedicationImages(
      @NonNull Long medicationId, List<MultipartFile> images) {
    log.debug("medicationId: {}", medicationId);

    Optional<MedicationEntity> medicationEntityOptional =
        medicationRepository.findById(medicationId);

    if (medicationEntityOptional.isEmpty()) {
      throw new DroneServiceException("medication by id '%d' not found");
    }

    MedicationEntity medicationEntity = medicationEntityOptional.get();

    Optional.ofNullable(images).stream()
        .flatMap(Collection::stream)
        .map(
            multipartFile ->
                medicationImageDataMapper.toMedicationImageEntity(multipartFile, medicationEntity))
        .collect(
            Collectors.collectingAndThen(Collectors.toList(), medicationImageRepository::saveAll));

    return new ResponseEntity<>(HttpStatus.OK);
  }

}
