/*
 * Rinat Muratidinov. Do not reproduce without permission in writing.
 * Copyright (c) 2022 RM. All rights reserved.
 */

package kg.rinat.model.schema;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * Drone schema
 *
 * @author Rinat Muratidinov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DroneSchema {

  @Schema(description = "Drone request data")
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class DroneRequestDto {

    @Schema(description = "Serial number")
    @Size(min = 1, max = 100)
    protected String serialNumber;

    @Schema(description = "Model")
    protected DroneModel model;

    @Schema(description = "Allowed weight")
    protected int allowedWeight;

    @Schema(description = "Battery level in percents")
    protected int batteryLevel;

    @Schema(description = "State")
    protected DroneState state;
  }

  @Schema(description = "Drone response data")
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @EqualsAndHashCode(callSuper = true)
  public static class DroneResponseDto extends DroneRequestDto {

    @Builder(builderMethodName = "droneResponseDtoBuilder")
    public DroneResponseDto(
        @Size(min = 1, max = 100) String serialNumber,
        DroneModel model,
        int allowedWeight,
        int batteryCapacity,
        DroneState state,
        int currentWeight) {
      super(serialNumber, model, allowedWeight, batteryCapacity, state);
      this.currentWeight = currentWeight;
    }

    @Schema(description = "Current weight")
    private int currentWeight;
  }

  @Schema(description = "Drone model")
  public enum DroneModel {

    @Schema(description = "Lightweight")
    LIGHTWEIGHT,

    @Schema(description = "Middleweight")
    MIDDLEWEIGHT,

    @Schema(description = "Cruiserweight")
    CRUISERWEIGHT,

    @Schema(description = "Heavyweight")
    HEAVYWEIGHT;
  }

  @Schema(description = "Drone state")
  public enum DroneState {

    @Schema(description = "Idle")
    IDLE,

    @Schema(description = "Loading")
    LOADING,

    @Schema(description = "Loaded")
    LOADED,

    @Schema(description = "Delivering")
    DELIVERING,

    @Schema(description = "Delivered")
    DELIVERED,

    @Schema(description = "Returning")
    RETURNING;
  }
}
