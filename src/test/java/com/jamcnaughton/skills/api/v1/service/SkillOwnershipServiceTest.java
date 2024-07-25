package com.jamcnaughton.skills.api.v1.service;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jamcnaughton.skills.api.v1.model.SkillOwnershipDto;
import com.jamcnaughton.skills.api.v1.repository.SkillOwnershipRepository;
import com.jamcnaughton.skills.model.entity.Person;
import com.jamcnaughton.skills.model.entity.Skill;
import com.jamcnaughton.skills.model.entity.SkillLevel;
import com.jamcnaughton.skills.model.entity.SkillOwnership;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

/** Skill ownership service tests. */
@DisplayName("Skill Ownership Service")
public class SkillOwnershipServiceTest {
  private final SkillOwnershipRepository skillOwnershipRepository =
          mock(SkillOwnershipRepository.class);
  private final EntityManager entityManager = mock(EntityManager.class);
  private SkillOwnershipService skillOwnershipService;
  private ArrayList<SkillOwnership> skillOwnerships;

  /** Setup. */
  @BeforeEach
  void setup() {
    skillOwnerships = new ArrayList<>();
    for (long i = 1; i <= 10; i++) {
      skillOwnerships.add(
          SkillOwnership.builder()
              .skillOwnershipId(i)
              .personId(i)
              .skillId(i)
              .skillLevelId(i)
              .person(Person.builder().personId(i).build())
              .skill(Skill.builder().skillId(i).build())
              .skillLevel(SkillLevel.builder().skillLevelId(i).build())
              .build());
    }
    Page<SkillOwnership> pagedSkillOwnerships = new PageImpl<>(skillOwnerships);
    when(skillOwnershipRepository.findByPersonIdAndSkillId(any(), any()))
            .thenReturn(Optional.ofNullable(skillOwnerships.get(0)));
    when(skillOwnershipRepository.findAll(any(Pageable.class))).thenReturn(pagedSkillOwnerships);
    when(skillOwnershipRepository.save(any()))
        .thenAnswer(invocation -> invocation.getArgument(0, SkillOwnership.class));
    when(skillOwnershipRepository.informativeDelete(1L, 1L)).thenReturn(1);
    skillOwnershipService =
            new SkillOwnershipService(skillOwnershipRepository, new ModelMapper(), entityManager);
  }

  /** Test find service method. */
  @Test
  @DisplayName("Find")
  void testFind() {
    SkillOwnershipDto result = skillOwnershipService.find(1L, 1L);
    assertEquals(1L, result.getSkillOwnershipId());
  }

  /** Test find service method when a skill ownership cannot be found. */
  @Test
  @DisplayName("Find - no skillOwnership found")
  void testFindNoSkillOwnership() {
    when(skillOwnershipRepository.findByPersonIdAndSkillId(any(), any()))
            .thenReturn(Optional.empty());
    Exception exception =
        assertThrows(
            NoSuchElementException.class,
            () -> {
              skillOwnershipService.find(1L, 1L);
            });
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains("No skill ownership found with person ID: 1, skill ID: 1"));
  }

  /** Test get all service method. */
  @Test
  @DisplayName("Get all")
  void testGetAll() {
    Pageable pageable = PageRequest.of(0, 10);
    PagedModel<SkillOwnershipDto> result = skillOwnershipService.getAll(pageable);
    assertEquals(10, result.getContent().size());
  }

  /** Test create service method. */
  @Test
  @DisplayName("Create")
  void testCreate() {
    when(skillOwnershipRepository.save(any())).thenReturn(skillOwnerships.get(0));
    SkillOwnershipDto result = skillOwnershipService.create(1L, 1L, 1L);
    assertEquals(1L, result.getSkill().getSkillId());
  }

  /** Test update service method. */
  @Test
  @DisplayName("Update")
  void testUpdateEmail() {
    SkillOwnershipDto result = skillOwnershipService.update(1L, 1L, 2L);
    assertEquals(1L, result.getSkillLevel().getSkillLevelId());
  }

  /** Test update service method when a skill ownership cannot be found. */
  @Test
  @DisplayName("Update - no skill ownership found")
  void testUpdateNoSkillOwnership() {
    when(skillOwnershipRepository.findByPersonIdAndSkillId(any(), any()))
            .thenReturn(Optional.empty());
    Exception exception =
        assertThrows(
            NoSuchElementException.class,
            () -> {
              skillOwnershipService.update(1L, 1L, 2L);
            });
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains("No skill ownership found with person ID: 1, skill ID: 1"));
  }

  /** Test delete service method. */
  @Test
  @DisplayName("Delete")
  void testDelete() {
    skillOwnershipService.delete(1L, 1L);
    verify(skillOwnershipRepository, times(1)).informativeDelete(1L, 1L);
  }

  /** Test delete service method when a skill ownership cannot be found. */
  @Test
  @DisplayName("Delete - no skill ownership found")
  void testDeleteNoSkillOwnership() {
    when(skillOwnershipRepository.informativeDelete(1L, 1L)).thenReturn(0);
    Exception exception =
        assertThrows(
            NoSuchElementException.class,
            () -> {
              skillOwnershipService.delete(1L, 1L);
            });
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains("No skill ownership found with person ID: 1, skill ID: 1"));
  }
}
