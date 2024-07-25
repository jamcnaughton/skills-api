package com.jamcnaughton.skills.api.v1.controller;

import com.jamcnaughton.skills.api.v1.model.SkillLevelDto;
import com.jamcnaughton.skills.api.v1.service.SkillLevelService;
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

/** Controller for skill level related requests. */
@Validated
@Tag(name = "${skill-level.name}", description = "${skill-level.info}")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/skill-level")
public class SkillLevelController {

  /** The service responsible for managing skill levels. */
  private final SkillLevelService skillLevelService;

  /**
   * The end-point for finding a specific skill level.
   *
   * @param skillLevelId The ID of the skill level to return.
   * @return A response containing the specified skill level.
   */
  @Operation(description = "${skill-level.find}")
  @ApiResponse(description = "${response.skill-level}")
  @GetMapping("/{skillLevelId}")
  public ResponseEntity<SkillLevelDto> find(
      @Parameter(description = "${param.skill-level-id}") @PathVariable long skillLevelId) {
    SkillLevelDto response = skillLevelService.find(skillLevelId);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * The end-point for getting all skill levels.
   *
   * @param pageable The object that enables pagination.
   * @return A response containing all skill levels.
   */
  @Operation(
      description = "${skill-level.get-all}",
      parameters = {
        @Parameter(name = "page", description = "${param.page}", example = "0"),
        @Parameter(name = "size", description = "${param.size}", example = "10")
      })
  @ApiResponse(description = "${response.skill-levels}")
  @GetMapping
  public ResponseEntity<PagedModel<SkillLevelDto>> getAll(
      @Parameter(hidden = true) final Pageable pageable) {
    return ResponseEntity.status(HttpStatus.OK).body(skillLevelService.getAll(pageable));
  }

  /**
   * The end-point for creating a new skill level.
   *
   * @param order The index of this new skill level in the order of all skill levels
   * @param description The description of the new skill level.
   * @return A response containing the created skill level.
   */
  @Operation(description = "${skill-level.create}")
  @ApiResponse(description = "${response.skill-level}")
  @PostMapping
  public ResponseEntity<SkillLevelDto> create(
      @Parameter(description = "${param.skill-level-order}") @RequestParam Long order,
      @Parameter(description = "${param.skill-level-description}") @RequestParam
          String description) {
    SkillLevelDto response = skillLevelService.create(order, description);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /**
   * The end-point for updating an existing skill level.
   *
   * @param skillLevelId The ID of the skill level to update.
   * @param order The new index of the skill level in the order of all skill levels (optional).
   * @param description The new description of the skill level (optional).
   * @return A response containing the modified skill level.
   */
  @Operation(description = "${skill-level.update}")
  @ApiResponse(description = "${response.skill-level}")
  @PutMapping("/{skillLevelId}")
  public ResponseEntity<SkillLevelDto> update(
      @Parameter(description = "${param.skill-level-id}") @PathVariable long skillLevelId,
      @Parameter(description = "${param.skill-level-order}") @RequestParam(required = false)
          Long order,
      @Parameter(description = "${param.skill-level-description}") @RequestParam(required = false)
          String description) {
    if (order == null && description == null) {
      throw new IllegalArgumentException("No order or description parameters supplied.");
    }
    SkillLevelDto response = skillLevelService.update(skillLevelId, order, description);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * The end-point for deleting a skill level.
   *
   * @param skillLevelId The ID of the skill level to delete.
   * @return A response containing a message declaring the result of the deletion.
   */
  @Operation(description = "${skill-level.delete}")
  @ApiResponse(description = "${response.delete}")
  @DeleteMapping("/{skillLevelId}")
  public ResponseEntity<String> delete(@PathVariable long skillLevelId) {
    skillLevelService.delete(skillLevelId);
    return ResponseEntity.status(HttpStatus.OK)
        .body("Skill level deletion request successfully processed.");
  }
}
