package com.jamcnaughton.skills.api.v1.service;

import com.jamcnaughton.skills.api.v1.model.SkillLevelDto;
import com.jamcnaughton.skills.api.v1.repository.SkillLevelRepository;
import com.jamcnaughton.skills.model.entity.SkillLevel;
import com.jamcnaughton.skills.model.entity.SkillOwnership;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

/** Service for skill levels. */
@Service
@AllArgsConstructor
public class SkillLevelService {

  /** Repository for handling skill level related queries. */
  private final SkillLevelRepository skillLevelRepository;

  /** Bean for converting between entities and DTOs. */
  @Autowired private ModelMapper modelMapper;

  /**
   * Find a specific skill level.
   *
   * @param skillLevelId The ID of the skill level to return.
   * @return The specified skill level.
   */
  public SkillLevelDto find(Long skillLevelId) {
    return convertToDto(get(skillLevelId));
  }

  /**
   * Get all skill levels.
   *
   * @param pageable The object that enables pagination
   * @return All skill levels.
   */
  public PagedModel<SkillLevelDto> getAll(final Pageable pageable) {
    return new PagedModel<>(skillLevelRepository.findAll(pageable)
        .map(skillLevel -> convertToDto(skillLevel)));
  }

  /**
   * Create a new skill level.
   *
   * @param order The index of this new skill level in the order of all skill levels
   * @param description The description of the new skill level.
   * @return The created skill level.
   */
  public SkillLevelDto create(Long order, String description) {
    SkillLevel skillLevel = new SkillLevel();
    skillLevel.setSkillLevelOrder(order);
    skillLevel.setSkillLevelDescription(description);
    return convertToDto(skillLevelRepository.save(skillLevel));
  }

  /**
   * Update an existing skill level.
   *
   * @param skillLevelId The ID of the skill level to update.
   * @param order The new index of the skill level in the order of all skill levels.
   * @param description The new description of the skill level.
   * @return The updated skill level.
   */
  public SkillLevelDto update(Long skillLevelId, Long order, String description) {
    SkillLevel skillLevel = get(skillLevelId);
    if (order != null) {
      skillLevel.setSkillLevelOrder(order);
    }
    if (description != null) {
      skillLevel.setSkillLevelDescription(description);
    }
    return convertToDto(skillLevelRepository.save(skillLevel));
  }

  /**
   * Delete a specific skill level.
   *
   * @param skillLevelId The ID of the skill level to delete.
   */
  public void delete(Long skillLevelId) {
    int result = skillLevelRepository.informativeDelete(skillLevelId);
    if (result != 1) {
      throw new NoSuchElementException("No skill level found with the ID: " + skillLevelId);
    }
  }

  /**
   * Find a specific skill level.
   *
   * @param skillLevelId The ID of the skill level to return.
   * @return The specified skill level.
   */
  private SkillLevel get(Long skillLevelId) {
    return skillLevelRepository
        .findById(skillLevelId)
        .orElseThrow(
            () -> new NoSuchElementException("No skill level found with the ID: " + skillLevelId));
  }

  /**
   * Tidy a skill level entity then convert it into a skill level DTO.
   *
   * @param skillLevel The entity to convert.
   * @return The DTO based on the entity.
   */
  private SkillLevelDto convertToDto(SkillLevel skillLevel) {
    if (skillLevel.getSkillOwnerships() != null) {
      for (SkillOwnership skillOwnership : skillLevel.getSkillOwnerships()) {
        skillOwnership.setSkillLevel(null);
        skillOwnership.getPerson().setSkillOwnerships(null);
        skillOwnership.getSkill().setSkillOwnerships(null);
      }
    }
    return modelMapper.map(skillLevel, SkillLevelDto.class);
  }
}
