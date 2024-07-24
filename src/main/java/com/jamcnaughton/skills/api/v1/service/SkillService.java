package com.jamcnaughton.skills.api.v1.service;

import com.jamcnaughton.skills.api.v1.model.SkillDto;
import com.jamcnaughton.skills.api.v1.repository.SkillRepository;
import com.jamcnaughton.skills.model.entity.Skill;
import com.jamcnaughton.skills.model.entity.SkillOwnership;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service for skills. */
@Service
@AllArgsConstructor
public class SkillService {

  /** Repository for handling skill related queries. */
  private final SkillRepository skillRepository;

  /** Bean for converting between entities and DTOs. */
  @Autowired private ModelMapper modelMapper;

  /**
   * Find a specific skill.
   *
   * @param skillId The ID of the skill to return.
   * @return The specified skill.
   */
  public SkillDto find(Long skillId) {
    return convertToDto(get(skillId));
  }

  /**
   * Get all skills.
   *
   * @return All skills.
   */
  public List<SkillDto> getAll() {
    return skillRepository.findAll().stream()
        .map(person -> convertToDto(person))
        .collect(Collectors.toList());
  }

  /**
   * Create a new skill.
   *
   * @param name The name of the new skill.
   * @return The created skill.
   */
  public SkillDto create(String name) {
    Skill skill = new Skill();
    skill.setSkillName(name);
    return convertToDto(skillRepository.save(skill));
  }

  /**
   * Update an existing skill.
   *
   * @param skillId The ID of the skill to update.
   * @param name The new name of the skill.
   * @return The updated skill.
   */
  public SkillDto update(Long skillId, String name) {
    Skill skill = get(skillId);
    skill.setSkillName(name);
    return convertToDto(skillRepository.save(skill));
  }

  /**
   * Delete a specific skill.
   *
   * @param skillId The ID of the skill to delete.
   */
  public void delete(Long skillId) {
    int result = skillRepository.informativeDelete(skillId);
    if (result != 1) {
      throw new NoSuchElementException("No skill found with the ID: " + skillId);
    }
  }

  /**
   * Find a specific skill.
   *
   * @param skillId The ID of the skill to return.
   * @return The specified skill.
   */
  private Skill get(Long skillId) {
    return skillRepository
        .findById(skillId)
        .orElseThrow(() -> new NoSuchElementException("No skill found with the ID: " + skillId));
  }

  /**
   * Tidy a skill entity then convert it into a skill DTO.
   *
   * @param skill The entity to convert.
   * @return The DTO based on the entity.
   */
  private SkillDto convertToDto(Skill skill) {
    if (skill.getSkillOwnerships() != null) {
      for (SkillOwnership skillOwnership : skill.getSkillOwnerships()) {
        skillOwnership.setSkill(null);
        skillOwnership.getPerson().setSkillOwnerships(null);
        skillOwnership.getSkillLevel().setSkillOwnerships(null);
      }
    }
    return modelMapper.map(skill, SkillDto.class);
  }
}
