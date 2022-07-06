/*
 * Rinat Muratidinov. Do not reproduce without permission in writing.
 * Copyright (c) 2022 RM. All rights reserved.
 */

package kg.rinat.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception handler
 *
 * @author Rinat Muratidinov
 */
@Slf4j
@ControllerAdvice
public class DroneExceptionHandler {

  @ExceptionHandler(value = {DroneServiceException.class, HttpMessageNotReadableException.class})
  protected ResponseEntity<String> handleDroneServiceException(DroneServiceException exception) {
    return handleException(exception, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {Exception.class})
  protected ResponseEntity<String> handleAllExceptions(Exception exception) {
    return handleException(exception, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handle exceptions
   *
   * @param exception Exception
   * @param httpStatus Http status
   * @return Response status and message
   */
  private ResponseEntity<String> handleException(Exception exception, HttpStatus httpStatus) {
    log.warn("exception: {}", exception.getMessage());

    return new ResponseEntity<>(exception.getMessage(), httpStatus);
  }
}
