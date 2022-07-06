/*
 * Rinat Muratidinov. Do not reproduce without permission in writing.
 * Copyright (c) 2022 RM. All rights reserved.
 */

package kg.rinat.dal.mapper;

import static kg.rinat.model.schema.DroneSchema.DroneRequestDto;
import static kg.rinat.model.schema.DroneSchema.DroneResponseDto;
import kg.rinat.dal.entity.DroneEntity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Drone data mapper
 *
 * @author Rinat Muratidinov
 */
@Slf4j
@Component
public class DroneDataMapper {

  /**
   * Map DB drone entity to response data
   *
   * @param droneEntity Entity from DB
   * @return Response data
   */
  public DroneResponseDto toDroneResponseDto(DroneEntity droneEntity) {
    log.debug("droneEntity: {}", droneEntity);

    if (droneEntity == null) {
      log.warn("droneEntity is null");
      return null;
    }

    return DroneResponseDto.droneResponseDtoBuilder()
        .serialNumber(droneEntity.getSerialNumber())
        .model(droneEntity.getModel())
        .allowedWeight(droneEntity.getAllowedWeight())
        .currentWeight(droneEntity.getCurrentWeight())
        .batteryCapacity(droneEntity.getBatteryLevel())
        .state(droneEntity.getState())
        .build();
  }

  /**
   * Map drone request data to DB entity
   *
   * @param droneRequestDto Drone request data
   * @return DB entity
   */
  public DroneEntity toDroneEntity(DroneRequestDto droneRequestDto) {
    log.debug("droneDto: {}", droneRequestDto);

    if (droneRequestDto == null) {
      log.warn("droneDto is null");
      return null;
    }

    return DroneEntity.builder()
        .serialNumber(droneRequestDto.getSerialNumber())
        .model(droneRequestDto.getModel())
        .currentWeight(0)
        .allowedWeight(droneRequestDto.getAllowedWeight())
        .batteryLevel(droneRequestDto.getBatteryLevel())
        .state(droneRequestDto.getState())
        .build();
  }
}
