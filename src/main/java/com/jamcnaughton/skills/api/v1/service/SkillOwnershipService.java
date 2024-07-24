package com.jamcnaughton.skills.api.v1.service;

import com.jamcnaughton.skills.api.v1.model.SkillOwnershipDto;
import com.jamcnaughton.skills.api.v1.repository.SkillOwnershipRepository;
import com.jamcnaughton.skills.model.entity.SkillOwnership;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service for skillOwnerships. */
@Service
@AllArgsConstructor
public class SkillOwnershipService {

  /** Repository for handling skill ownership related queries. */
  private final SkillOwnershipRepository skillOwnershipRepository;

  /** Bean for converting between entities and DTOs. */
  @Autowired private ModelMapper modelMapper;

  @PersistenceContext
  private EntityManager entityManager;

  /**
   * Find a specific skill ownership.
   *
   * @param personId The ID of the person affiliated with the skill ownership to return.
   * @param skillId The ID of the skill affiliated with the skill ownership to return.
   * @return The specified skill ownership.
   */
  public SkillOwnershipDto find(Long personId, Long skillId) {
    return convertToDto(get(personId, skillId));
  }

  /**
   * Get all skillOwnerships.
   *
   * @return All skillOwnerships.
   */
  public List<SkillOwnershipDto> getAll() {
    return skillOwnershipRepository.findAll().stream()
        .map(skillOwnership -> convertToDto(skillOwnership))
        .collect(Collectors.toList());
  }

  /**
   * Create a new skill ownership.
   *
   * @param personId The ID of the person to affiliate with the new skill ownership.
   * @param skillId The ID of the skill to affiliate with the new skill ownership.
   * @param skillLevelId The ID of the skill level to affiliate with the new skill ownership.
   * @return The created skill ownership.
   */
  @Transactional
  public SkillOwnershipDto create(Long personId, Long skillId, Long skillLevelId) {
    SkillOwnership skillOwnership = new SkillOwnership();
    skillOwnership.setPersonId(personId);
    skillOwnership.setSkillId(skillId);
    skillOwnership.setSkillLevelId(skillLevelId);
    skillOwnership = skillOwnershipRepository.save(skillOwnership);
    entityManager.refresh(skillOwnership);
    entityManager.detach(skillOwnership);
    return convertToDto(skillOwnership);
  }

  /**
   * Update an existing skillOwnership.
   *
   * @param personId The ID of the person affiliated with the skill ownership to update.
   * @param skillId The ID of the skill affiliated with the skill ownership to update.
   * @param skillLevelId The ID of the skill level to affiliate with the skill ownership.
   * @return The updated skillOwnership.
   */
  @Transactional
  public SkillOwnershipDto update(Long personId, Long skillId, Long skillLevelId) {
    SkillOwnership skillOwnership = get(personId, skillId);
    skillOwnership.setSkillLevelId(skillLevelId);
    skillOwnershipRepository.saveAndFlush(skillOwnership);
    entityManager.refresh(skillOwnership);
    entityManager.detach(skillOwnership);
    return convertToDto(skillOwnership);
  }

  /**
   * Delete a specific skillOwnership.
   *
   * @param personId The ID of the person affiliated with the skill ownership to delete.
   * @param skillId The ID of the skill affiliated with the skill ownership to delete.
   */
  public void delete(Long personId, Long skillId) {
    int result = skillOwnershipRepository.informativeDelete(personId, skillId);
    if (result != 1) {
      throw new NoSuchElementException(
          "No skill ownership found with person ID: " + personId + ", skill ID: " + skillId);
    }
  }

  /**
   * Find a specific skill ownership.
   *
   * @param personId The ID of the person affiliated with the skill ownership to return.
   * @param skillId The ID of the skill affiliated with the skill ownership to return.
   * @return The specified skill ownership.
   */
  private SkillOwnership get(Long personId, Long skillId) {
    return skillOwnershipRepository
        .findByPersonIdAndSkillId(personId, skillId)
        .orElseThrow(
            () ->
                new NoSuchElementException(
                    "No skill ownership found with person ID: "
                        + personId
                        + ", skill ID: "
                        + skillId));
  }

  /**
   * Tidy a skillOwnership entity then convert it into a skillOwnership DTO.
   *
   * @param skillOwnership The entity to convert.
   * @return The DTO based on the entity.
   */
  private SkillOwnershipDto convertToDto(SkillOwnership skillOwnership) {
    skillOwnership.getPerson().setSkillOwnerships(null);
    skillOwnership.getSkill().setSkillOwnerships(null);
    skillOwnership.getSkillLevel().setSkillOwnerships(null);
    return modelMapper.map(skillOwnership, SkillOwnershipDto.class);
  }
}
