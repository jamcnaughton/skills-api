package com.jamcnaughton.skills.api.v1.exception;

import java.util.NoSuchElementException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** Advisor to catch and handle exceptions thrown. */
@ControllerAdvice
public class ExceptionControllerAdvisor {

  /**
   * A handler to catch and handle NoSuchElementException exceptions.
   *
   * @param ex The exception to catch.
   * @return Response entity which contains the HTTP response for not found.
   */
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException ex) {
    return buildResponse(ex, HttpStatus.NOT_FOUND);
  }

  /**
   * A handler to catch and handle DataIntegrityViolationException exceptions.
   *
   * @param ex The exception to catch.
   * @return Response entity which contains the HTTP response for a conflict.
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
    return buildResponse(ex, HttpStatus.CONFLICT);
  }

  /**
   * A handler to catch and handle IllegalArgumentException exceptions.
   *
   * @param ex The exception to catch.
   * @return Response entity which contains the HTTP response for bad request.
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
    return buildResponse(ex, HttpStatus.BAD_REQUEST);
  }

  /**
   * Build a response containing information on the exception.
   *
   * @param ex The exception to respond about.
   * @param status The status affiliated with the exception.
   * @return The response object.
   */
  private ResponseEntity<?> buildResponse(Exception ex, HttpStatus status) {
    return ResponseEntity.status(status).body(ex.getMessage());
  }
}
