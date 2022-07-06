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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Medication schema
 *
 * @author Rinat Muratidinov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MedicationSchema {

  @Schema(description = "Medication request data")
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MedicationRequestDto {

    @NotNull
    @Pattern(regexp = "[A-Za-z0-9_-]+")
    @Schema(description = "Name", example = "Tablet34")
    protected String name;

    @NotNull
    @Schema(description = "Weight")
    protected int weight;

    @NotNull
    @Pattern(regexp = "[A-Z0-9_]+")
    @Schema(description = "Code")
    protected String code;
  }

  @Schema(description = "Medication response data")
  @Data
  @NoArgsConstructor
  @EqualsAndHashCode(callSuper = true)
  public static class MedicationResponseDto extends MedicationRequestDto {

    @Builder(builderMethodName = "medicationResponseDtoBuilder")
    public MedicationResponseDto(
        @NotNull @Pattern(regexp = "[A-Za-z0-9_-]+") String name,
        @NotNull int weight,
        @NotNull @Pattern(regexp = "[A-Z0-9_]+") String code,
        List<byte[]>  images) {
      super(name, weight, code);
      this.images = images;
    }

    @NotNull
    @Schema(description = "Images")
    private List<byte[]> images;
  }
}
