package com.jamcnaughton.skills.api.v1.controller;

import com.jamcnaughton.skills.api.v1.model.SkillDto;
import com.jamcnaughton.skills.api.v1.service.SkillService;
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

/** Controller for skill related requests. */
@Validated
@Tag(name = "${skill.name}", description = "${skill.info}")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/skill")
public class SkillController {

  /** The service responsible for managing skills. */
  private final SkillService skillService;

  /**
   * The end-point for finding a specific skill.
   *
   * @param skillId The ID of the skill to return.
   * @return A response containing the specified skill.
   */
  @Operation(description = "${skill.find}")
  @ApiResponse(description = "${response.skill}")
  @GetMapping("/{skillId}")
  public ResponseEntity<SkillDto> find(
      @Parameter(description = "${param.skill-id}") @PathVariable long skillId) {
    SkillDto response = skillService.find(skillId);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * The end-point for getting all skills.
   *
   * @param pageable The object that enables pagination.
   * @return A response containing all skills.
   */
  @Operation(
      description = "${skill.get-all}",
      parameters = {
        @Parameter(name = "page", description = "${param.page}", example = "0"),
        @Parameter(name = "size", description = "${param.size}", example = "10")
      })
  @ApiResponse(description = "${response.skills}")
  @GetMapping
  public ResponseEntity<PagedModel<SkillDto>> getAll(
      @Parameter(hidden = true) final Pageable pageable) {
    return ResponseEntity.status(HttpStatus.OK).body(skillService.getAll(pageable));
  }

  /**
   * The end-point for creating a new skill.
   *
   * @param name The name of the new skill.
   * @return A response containing the created skill.
   */
  @Operation(description = "${skill.create}")
  @ApiResponse(description = "${response.skill}")
  @PostMapping
  public ResponseEntity<SkillDto> create(
      @Parameter(description = "${param.skill-name}") @RequestParam String name) {
    SkillDto response = skillService.create(name);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /**
   * The end-point for updating an existing skill.
   *
   * @param skillId The ID of the skill to update.
   * @param name The new name of the skill.
   * @return A response containing the modified skill.
   */
  @Operation(description = "${skill.update}")
  @ApiResponse(description = "${response.skill}")
  @PutMapping("/{skillId}")
  public ResponseEntity<SkillDto> update(
      @Parameter(description = "${param.skill-id}") @PathVariable long skillId,
      @Parameter(description = "${param.skill-name}") @RequestParam String name) {
    SkillDto response = skillService.update(skillId, name);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * The end-point for deleting a skill.
   *
   * @param skillId The ID of the skill to delete.
   * @return A response containing a message declaring the result of the deletion.
   */
  @Operation(description = "${skill.delete}")
  @ApiResponse(description = "${response.delete}")
  @DeleteMapping("/{skillId}")
  public ResponseEntity<String> delete(@PathVariable long skillId) {
    skillService.delete(skillId);
    return ResponseEntity.status(HttpStatus.OK)
        .body("Skill deletion request successfully processed.");
  }
}
