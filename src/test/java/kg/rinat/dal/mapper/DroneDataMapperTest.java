/*
 * Rinat Muratidinov. Do not reproduce without permission in writing.
 * Copyright (c) 2022 RM. All rights reserved.
 */

package kg.rinat.dal.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import kg.rinat.dal.entity.DroneEntity;
import kg.rinat.model.schema.DroneSchema;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * DroneDataMapper junit test
 *
 * @author Rinat Muratidinov
 */
class DroneDataMapperTest {

  private DroneDataMapper droneDataMapper = new DroneDataMapper();

  @Test
  @DisplayName("Mapping drone DB entity to request data")
  void toDroneResponseDto() {
    // GIVEN
    DroneEntity droneEntity = createDroneEntity();

    // WHEN
    DroneSchema.DroneResponseDto droneResponseDto = droneDataMapper.toDroneResponseDto(droneEntity);

    // THEN
    assertEquals(droneEntity.getSerialNumber(), droneResponseDto.getSerialNumber());
    assertEquals(droneEntity.getCurrentWeight(), droneResponseDto.getCurrentWeight());
    assertEquals(droneEntity.getAllowedWeight(), droneResponseDto.getAllowedWeight());
    assertEquals(droneEntity.getBatteryLevel(), droneResponseDto.getBatteryLevel());
    assertEquals(droneEntity.getModel(), droneResponseDto.getModel());
    assertEquals(droneEntity.getState(), droneResponseDto.getState());
  }

  @Test
  @DisplayName("Mapping drone request data to DB entity")
  void toDroneEntity() {
    // GIVEN
    DroneSchema.DroneRequestDto droneRequestDto = createDroneRequestDto();

    // WHEN
    DroneEntity droneEntity = droneDataMapper.toDroneEntity(droneRequestDto);

    // THEN
    assertEquals(droneRequestDto.getSerialNumber(), droneEntity.getSerialNumber());
    assertEquals(0, droneEntity.getCurrentWeight());
    assertEquals(droneRequestDto.getAllowedWeight(), droneEntity.getAllowedWeight());
    assertEquals(droneRequestDto.getBatteryLevel(), droneEntity.getBatteryLevel());
    assertEquals(droneRequestDto.getModel(), droneEntity.getModel());
    assertEquals(droneRequestDto.getState(), droneEntity.getState());
  }

  private DroneSchema.DroneResponseDto createDroneResponseDto() {
    return DroneSchema.DroneResponseDto.droneResponseDtoBuilder()
        .serialNumber("1")
        .currentWeight(12)
        .allowedWeight(15)
        .batteryCapacity(45)
        .model(DroneSchema.DroneModel.HEAVYWEIGHT)
        .state(DroneSchema.DroneState.IDLE)
        .build();
  }

  private DroneSchema.DroneRequestDto createDroneRequestDto() {
    return DroneSchema.DroneRequestDto.builder()
        .serialNumber("1")
        .model(DroneSchema.DroneModel.HEAVYWEIGHT)
        .allowedWeight(45)
        .batteryLevel(45)
        .state(DroneSchema.DroneState.IDLE)
        .build();
  }

  private DroneEntity createDroneEntity() {
    return DroneEntity.builder()
        .serialNumber("1")
        .model(DroneSchema.DroneModel.HEAVYWEIGHT)
        .allowedWeight(45)
        .currentWeight(15)
        .batteryLevel(45)
        .state(DroneSchema.DroneState.IDLE)
        .build();
  }
}
