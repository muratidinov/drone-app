/*
 * Rinat Muratidinov. Do not reproduce without permission in writing.
 * Copyright (c) 2022 RM. All rights reserved.
 */

package kg.rinat.service.impl;

import static kg.rinat.model.schema.DroneSchema.DroneRequestDto;
import static kg.rinat.model.schema.DroneSchema.DroneResponseDto;
import static kg.rinat.model.schema.DroneSchema.DroneState;
import static kg.rinat.model.schema.MedicationSchema.MedicationRequestDto;
import kg.rinat.dal.entity.DroneEntity;
import kg.rinat.dal.entity.MedicationEntity;
import kg.rinat.dal.mapper.DroneDataMapper;
import kg.rinat.dal.mapper.MedicationDataMapper;
import kg.rinat.dal.repository.DroneRepository;
import kg.rinat.dal.repository.MedicationRepository;
import kg.rinat.exception.DroneServiceException;
import kg.rinat.service.api.DroneService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Drones service
 *
 * @author Rinat Muratidinov
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {

  /** Drone repository */
  private final DroneRepository droneRepository;

  /** Medication repository */
  private final MedicationRepository medicationRepository;

  /** Drone data mapper */
  private final DroneDataMapper droneDataMapper;

  /** Medication data mapper */
  private final MedicationDataMapper medicationDataMapper;

  /**
   * Registering drone
   *
   * @param droneRequestDto Drone request data
   * @return Serial number of registered drone
   */
  @Override
  @Transactional
  public String registerDrone(@NonNull DroneRequestDto droneRequestDto) {
    log.debug("droneRequestDto: {}", droneRequestDto);

    String serialNumber = droneRequestDto.getSerialNumber();

    if (StringUtils.isBlank(serialNumber)) {
      throw new DroneServiceException("serial number is not valid");
    }

    boolean droneExists = droneRepository.findById(serialNumber).isPresent();

    if (droneExists) {
      throw new DroneServiceException(
          String.format("drone with serial number '%s' already exists", serialNumber));
    }

    return Optional.of(droneRequestDto)
        .map(droneDataMapper::toDroneEntity)
        .map(droneRepository::save)
        .map(DroneEntity::getSerialNumber)
        .orElseThrow(() -> new DroneServiceException("exception during saving drone"));
  }

  /**
   * Load medications to drone
   *
   * @param droneSerialNumber Drones serial number
   * @param medicationRequestDtoList Medications data
   * @return Http status
   */
  @Override
  @Transactional
  public ResponseEntity<Void> loadMedications(
      String droneSerialNumber, List<MedicationRequestDto> medicationRequestDtoList) {
    log.debug(
        "droneSerialNumber: {}, medicationsRequestDto: {}",
        droneSerialNumber,
        medicationRequestDtoList);

    if (StringUtils.isBlank(droneSerialNumber) || medicationRequestDtoList == null) {
      throw new DroneServiceException("drone serial number is blank or medication data is empty");
    }

    DroneEntity droneEntity = findDroneBySerialNumber(droneSerialNumber);

    Integer medicationsSumWeight =
        medicationRequestDtoList.stream()
            .map(MedicationRequestDto::getWeight)
            .reduce(Integer::sum)
            .orElse(0);

    int sumWeight = medicationsSumWeight + droneEntity.getCurrentWeight();

    int allowedWeight = droneEntity.getAllowedWeight();

    if (sumWeight > allowedWeight) {
      throw new DroneServiceException(
          String.format(
              "weight of medications (%d) is more than allowed by drone (%d)",
              sumWeight, allowedWeight));
    }

    if (sumWeight == allowedWeight) {
      droneEntity.setState(DroneState.LOADED);
    } else {
      droneEntity.setState(DroneState.LOADING);
    }

    droneEntity.setCurrentWeight(sumWeight);

    droneRepository.save(droneEntity);

    List<MedicationEntity> medicationEntityList =
        medicationRequestDtoList.stream()
            .map(
                medicationRequestDto ->
                    medicationDataMapper.toMedicationEntity(medicationRequestDto, droneEntity))
            .collect(Collectors.toList());

    medicationRepository.saveAll(medicationEntityList);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Getting available drones for loading medications
   *
   * @return Drones list
   */
  @Override
  @Transactional(readOnly = true)
  public List<DroneResponseDto> getAvailableDrones() {
    log.debug("getting available drones...");

    return droneRepository.findByStateIn(List.of(DroneState.IDLE, DroneState.LOADING)).stream()
        .map(droneDataMapper::toDroneResponseDto)
        .collect(Collectors.toList());
  }

  /**
   * Getting battery level of drone
   *
   * @param droneSerialNumber Drones serial number
   * @return Battery level in percent
   */
  @Override
  @Transactional(readOnly = true)
  public Integer getBatteryLevel(String droneSerialNumber) {
    log.debug("droneSerialNumber: {}", droneSerialNumber);

    return findDroneBySerialNumber(droneSerialNumber).getBatteryLevel();
  }

  /**
   * Finding drone by serial number
   *
   * @param droneSerialNumber Drones serial number
   * @return Drone DB entity
   */
  private DroneEntity findDroneBySerialNumber(String droneSerialNumber) {

    if (StringUtils.isBlank(droneSerialNumber)) {
      throw new DroneServiceException("droneSerialNumber is blank", HttpStatus.NOT_FOUND);
    }

    Optional<DroneEntity> droneBySerialNumber = droneRepository.findById(droneSerialNumber);

    if (droneBySerialNumber.isEmpty()) {
      throw new DroneServiceException(
          String.format("by given drone serial number '%s' nothing found", droneSerialNumber),
          HttpStatus.NOT_FOUND);
    }

    return droneBySerialNumber.get();
  }
}
