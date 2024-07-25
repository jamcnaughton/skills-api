package com.jamcnaughton.skills.api.v1.controller;

import com.jamcnaughton.skills.api.v1.model.SkillOwnershipDto;
import com.jamcnaughton.skills.api.v1.service.SkillOwnershipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
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

/** Controller for skill ownership related requests. */
@Validated
@Tag(name = "${skill-ownership.name}", description = "${skill-ownership.info}")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/skill-ownership")
public class SkillOwnershipController {

  /** The service responsible for managing skill ownerships. */
  private final SkillOwnershipService skillOwnershipService;

  /**
   * The end-point for finding a specific skill ownership.
   *
   * @param personId The ID of the person affiliated with the skill ownership to return.
   * @param skillId The ID of the skill affiliated with the skill ownership to return.
   * @return A response containing the specified skill ownership.
   */
  @Operation(description = "${skill-ownership.find}")
  @ApiResponse(description = "${response.skill-ownership}")
  @GetMapping("/{personId}/{skillId}")
  public ResponseEntity<SkillOwnershipDto> find(
      @Parameter(description = "${param.person-id}") @PathVariable long personId,
      @Parameter(description = "${param.skill-id}") @PathVariable long skillId) {
    SkillOwnershipDto response = skillOwnershipService.find(personId, skillId);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * The end-point for getting all skill ownership.
   *
   * @param pageable The object that enables pagination.
   * @return A response containing all skill ownerships.
   */
  @Operation(
      description = "${skill-ownership.get-all}",
      parameters = {
        @Parameter(name = "page", description = "${param.page}", example = "0"),
        @Parameter(name = "size", description = "${param.size}", example = "10")
      })
  @ApiResponse(description = "${response.skill-ownerships}")
  @GetMapping
  public ResponseEntity<PagedModel<SkillOwnershipDto>> getAll(
      @Parameter(hidden = true) final Pageable pageable) {
    return ResponseEntity.status(HttpStatus.OK).body(skillOwnershipService.getAll(pageable));
  }

  /**
   * The end-point for creating a new skill ownership.
   *
   * @param personId The ID of the person to affiliate with the new skill ownership.
   * @param skillId The ID of the skill to affiliate with the new skill ownership.
   * @param skillLevelId The ID of the skill level to affiliate with the new skill ownership.
   * @return A response containing the created skill ownership.
   */
  @Operation(description = "${skill-ownership.create}")
  @ApiResponse(description = "${response.skill-ownership}")
  @PostMapping
  public ResponseEntity<SkillOwnershipDto> create(
      @Parameter(description = "${param.person-id}") @RequestParam long personId,
      @Parameter(description = "${param.skill-id}") @RequestParam long skillId,
      @Parameter(description = "${param.skill-level-id}") @RequestParam long skillLevelId) {
    SkillOwnershipDto response = skillOwnershipService.create(personId, skillId, skillLevelId);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /**
   * The end-point for updating an existing skill ownership.
   *
   * @param personId The ID of the person affiliated with the skill ownership to update.
   * @param skillId The ID of the skill affiliated with the skill ownership to update.
   * @param skillLevelId The ID of the skill level to affiliate with the skill ownership.
   * @return A response containing the modified skill ownership.
   */
  @Operation(description = "${skill-ownership.update}")
  @ApiResponse(description = "${response.skill-ownership}")
  @PutMapping("/{personId}/{skillId}")
  public ResponseEntity<SkillOwnershipDto> update(
      @Parameter(description = "${param.person-id}") @PathVariable long personId,
      @Parameter(description = "${param.skill-id}") @PathVariable long skillId,
      @Parameter(description = "${param.skill-level-id}") @RequestParam long skillLevelId) {
    SkillOwnershipDto response = skillOwnershipService.update(personId, skillId, skillLevelId);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * The end-point for deleting a skill ownership.
   *
   * @param personId The ID of the person affiliated with the skill ownership to delete.
   * @param skillId The ID of the skill affiliated with the skill ownership to delete.
   * @return A response containing a message declaring the result of the deletion.
   */
  @Operation(description = "${skill-ownership.delete}")
  @ApiResponse(description = "${response.delete}")
  @DeleteMapping("/{personId}/{skillId}")
  public ResponseEntity<String> delete(
      @Parameter(description = "${param.person-id}") @PathVariable long personId,
      @Parameter(description = "${param.skill-id}") @PathVariable long skillId) {
    skillOwnershipService.delete(personId, skillId);
    return ResponseEntity.status(HttpStatus.OK)
        .body("Skill ownership deletion request successfully processed.");
  }
}
