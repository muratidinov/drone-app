/*
 * Rinat Muratidinov. Do not reproduce without permission in writing.
 * Copyright (c) 2022 RM. All rights reserved.
 */

package kg.rinat.controller;

import static kg.rinat.model.schema.DroneSchema.DroneResponseDto;
import static kg.rinat.model.schema.MedicationSchema.MedicationRequestDto;
import static kg.rinat.model.schema.MedicationSchema.MedicationResponseDto;
import kg.rinat.model.schema.DroneSchema.DroneRequestDto;
import kg.rinat.service.api.DroneService;
import kg.rinat.service.api.MedicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * Drones REST Controller
 *
 * @author Rinat Muratidinov
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/drone")
@RequiredArgsConstructor
public class DroneController {

  /** Drones service */
  private final DroneService droneService;

  /** Drones service */
  private final MedicationService medicationService;

  @Operation(
      summary = "Register drone",
      responses =
          @ApiResponse(
              responseCode = "200",
              description = "Successfully registered. Returns serial number of registered drone",
              content = @Content(schema = @Schema(example = "12345"))))
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public String registerDrone(
      @Parameter(description = "Drone data") @RequestBody @Valid DroneRequestDto droneRequestDto) {
    log.info("droneDto: {}", droneRequestDto);

    return droneService.registerDrone(droneRequestDto);
  }

  @Operation(
      summary = "Load medications",
      responses =
          @ApiResponse(
              responseCode = "200",
              description =
                  "Drone by serial number successfully found and medications successfully loaded into drone"))
  @PutMapping(
      value = "/medication/{drone-serial-number}",
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> loadMedication(
      @Parameter(description = "Drone serial number") @PathVariable(name = "drone-serial-number")
          String droneSerialNumber,
      @Parameter(description = "Medications data") @RequestBody @Valid
          List<MedicationRequestDto> medicationRequestDtoList) {
    log.info(
        "droneSerialNumber: {}, medicationsRequestDto: {}",
        droneSerialNumber,
        medicationRequestDtoList);

    return droneService.loadMedications(droneSerialNumber, medicationRequestDtoList);
  }

  @Operation(
      summary = "Load medication images",
      responses =
          @ApiResponse(
              responseCode = "200",
              description =
                  "Medication by id successfully found and medication images successfully uploaded"))
  @PutMapping(
      value = "/medication/image/{medication-id}",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> loadMedicationImages(
      @Parameter(description = "Medication id") @PathVariable(name = "medication-id")
          Long medicationId,
      @Parameter(description = "Medication images") @RequestParam List<MultipartFile> images) {
    log.info("medicationId: {}", medicationId);

    return medicationService.loadMedicationImages(medicationId, images);
  }

  @Operation(
      summary = "Get free / loading drones",
      responses =
          @ApiResponse(
              responseCode = "200",
              description = "Available drones found",
              content =
                  @Content(
                      array =
                          @ArraySchema(schema = @Schema(implementation = DroneResponseDto.class)))))
  @GetMapping
  public List<DroneResponseDto> getAvailableDrones() {
    log.info("getting available drones...");

    return droneService.getAvailableDrones();
  }

  @Operation(
      summary = "Get medication list of drone",
      responses =
          @ApiResponse(
              responseCode = "200",
              description = "Drone and medications found by serial number",
              content =
                  @Content(
                      array =
                          @ArraySchema(
                              schema = @Schema(implementation = MedicationResponseDto.class)))))
  @GetMapping("/medication/{drone-serial-number}")
  public List<MedicationResponseDto> getMedicationList(
      @Parameter(description = "Drone serial number")
          @PathVariable(name = "drone-serial-number")
          @Schema(maxLength = 100)
          String droneSerialNumber,
      @Parameter(description = "Need to load images?") @RequestParam boolean withImages) {
    log.info("droneSerialNumber: {}", droneSerialNumber);

    return medicationService.getMedicationList(droneSerialNumber, withImages);
  }

  @Operation(
      summary = "Get battery level of drone",
      responses =
          @ApiResponse(
              responseCode = "200",
              description = "Drone found by serial number and returned his battery level",
              content =
                  @Content(array = @ArraySchema(schema = @Schema(implementation = Integer.class)))))
  @GetMapping("/battery/{drone-serial-number}")
  public Integer getBatteryLevel(
      @Parameter(description = "Drone serial number")
          @PathVariable(name = "drone-serial-number")
          @Schema(maxLength = 100)
          String droneSerialNumber) {
    log.info("droneSerialNumber: {}", droneSerialNumber);

    return droneService.getBatteryLevel(droneSerialNumber);
  }
}
