/*
 * Rinat Muratidinov. Do not reproduce without permission in writing.
 * Copyright (c) 2022 RM. All rights reserved.
 */

package kg.rinat.dal.mapper;

import static kg.rinat.model.schema.MedicationSchema.MedicationRequestDto;
import static kg.rinat.model.schema.MedicationSchema.MedicationResponseDto;
import kg.rinat.dal.entity.DroneEntity;
import kg.rinat.dal.entity.MedicationEntity;
import kg.rinat.dal.entity.MedicationImageEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Medication data mapper
 *
 * @author Rinat Muratidinov
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MedicationDataMapper {

  /**
   * Build medication response data
   *
   * @param medicationEntity Medication DB entity
   * @param withImages Need to fill images field?
   * @return Medication response data
   */
  public MedicationResponseDto toMedicationResponseDto(
      MedicationEntity medicationEntity, boolean withImages) {
    log.debug("medicationEntity: {}", medicationEntity);

    if (medicationEntity == null) {
      log.warn("medicationEntity is null");
      return null;
    }

    List<byte[]> images =
        withImages
            ? Optional.ofNullable(medicationEntity.getImages()).stream()
                .flatMap(Collection::stream)
                .map(MedicationImageEntity::getImage)
                .collect(Collectors.toList())
            : Collections.emptyList();

    return MedicationResponseDto.medicationResponseDtoBuilder()
        .name(medicationEntity.getName())
        .weight(medicationEntity.getWeight())
        .code(medicationEntity.getCode())
        .images(images)
        .build();
  }

  /**
   * Build medication DB entity
   *
   * @param medicationRequestDto Medication request data
   * @param droneEntity Drone DB entity
   * @return Medication DB entity
   */
  public MedicationEntity toMedicationEntity(
      MedicationRequestDto medicationRequestDto, DroneEntity droneEntity) {
    log.debug("medicationRequestDto: {}", medicationRequestDto);

    if (medicationRequestDto == null) {
      log.warn("medication data is null");
      return null;
    }

    return MedicationEntity.builder()
        .name(medicationRequestDto.getName())
        .weight(medicationRequestDto.getWeight())
        .code(medicationRequestDto.getCode())
        .drone(droneEntity)
        .build();
  }
}
