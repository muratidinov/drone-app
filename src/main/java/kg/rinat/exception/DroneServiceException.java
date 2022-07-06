/*
 * Rinat Muratidinov. Do not reproduce without permission in writing.
 * Copyright (c) 2022 RM. All rights reserved.
 */

package kg.rinat.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * Drone service exception
 *
 * @author Rinat Muratidinov
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DroneServiceException extends RuntimeException {

  /** Exception message */
  private String message;

  /** Http status, that should be returned */
  private HttpStatus httpStatus;

  public DroneServiceException(String message) {
    super(message);
    this.message = message;
  }

  public DroneServiceException(String message, HttpStatus httpStatus) {
    super(message);
    this.message = message;
    this.httpStatus = httpStatus;
  }
}
