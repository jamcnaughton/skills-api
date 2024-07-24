package com.jamcnaughton.skills.api.v1.controller;

import com.jamcnaughton.skills.api.v1.model.PersonDto;
import com.jamcnaughton.skills.api.v1.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Controller for person related requests. */
@Validated
@Tag(name = "${person.name}", description = "${person.info}")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/person")
public class PersonController {

  /** The service responsible for managing persons. */
  private final PersonService personService;

  /**
   * The end-point for finding a specific person.
   *
   * @param personId The ID of the person to return.
   * @return A response containing the specified person.
   */
  @Operation(description = "${person.find}")
  @ApiResponse(description = "${response.person}")
  @GetMapping("/{personId}")
  public ResponseEntity<PersonDto> find(
      @Parameter(description = "${param.person-id}") @PathVariable long personId) {
    PersonDto response = personService.find(personId);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * The end-point for getting all persons.
   *
   * @return A response containing all persons.
   */
  @Operation(description = "${person.get-all}")
  @ApiResponse(description = "${response.persons}")
  @GetMapping
  public ResponseEntity<List<PersonDto>> getAll() {
    List<PersonDto> response = personService.getAll();
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * The end-point for creating a new person.
   *
   * @param email The email address of the new person.
   * @param forenames The forename(s) of the new person.
   * @param surname The surname address of the new person.
   * @return A response containing the created person.
   */
  @Operation(description = "${person.create}")
  @ApiResponse(description = "${response.person}")
  @PostMapping
  public ResponseEntity<PersonDto> create(
      @Parameter(description = "${param.email}") @RequestParam String email,
      @Parameter(description = "${param.forenames}") @RequestParam String forenames,
      @Parameter(description = "${param.surname}") @RequestParam String surname) {
    PersonDto response = personService.create(email, forenames, surname);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /**
   * The end-point for updating an existing person.
   *
   * @param personId The ID of the person to update.
   * @param email The new email address of the person (optional).
   * @param forenames The new forename(s) of the person (optional).
   * @param surname The new surname address of the person (optional).
   * @return A response containing the modified person.
   */
  @Operation(description = "${person.update}")
  @ApiResponse(description = "${response.person}")
  @PutMapping("/{personId}")
  public ResponseEntity<PersonDto> update(
      @Parameter(description = "${param.person-id}") @PathVariable long personId,
      @Parameter(description = "${param.email}") @RequestParam(required = false)
          String email,
      @Parameter(description = "${param.forenames}") @RequestParam(required = false)
          String forenames,
      @Parameter(description = "${param.surname}") @RequestParam(required = false)
          String surname) {
    PersonDto response = personService.update(personId, email, forenames, surname);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * The end-point for deleting a person.
   *
   * @param personId The ID of the person to delete.
   * @return A response containing a message declaring the result of the deletion.
   */
  @Operation(description = "${person.delete}")
  @ApiResponse(description = "${response.delete}")
  @DeleteMapping("/{personId}")
  public ResponseEntity<String> delete(@PathVariable long personId) {
    personService.delete(personId);
    return ResponseEntity.status(HttpStatus.OK)
        .body("Person deletion request successfully processed.");
  }
}
