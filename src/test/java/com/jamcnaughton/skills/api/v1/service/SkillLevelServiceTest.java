package com.jamcnaughton.skills.api.v1.service;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jamcnaughton.skills.api.v1.model.SkillLevelDto;
import com.jamcnaughton.skills.api.v1.repository.SkillLevelRepository;
import com.jamcnaughton.skills.model.entity.Person;
import com.jamcnaughton.skills.model.entity.Skill;
import com.jamcnaughton.skills.model.entity.SkillLevel;
import com.jamcnaughton.skills.model.entity.SkillOwnership;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

/** Skill level service tests. */
@DisplayName("Skill Level Service")
public class SkillLevelServiceTest {
  private final SkillLevelRepository skillLevelRepository = mock(SkillLevelRepository.class);
  private SkillLevelService skillLevelService;

  /** Setup. */
  @BeforeEach
  void setup() {
    ArrayList<SkillLevel> skillLevels = new ArrayList<>();
    for (long i = 1; i <= 10; i++) {
      Set<SkillOwnership> skillOwnerships = null;
      if (i % 2 == 0) {
        skillOwnerships =
            Sets.set(
                SkillOwnership.builder()
                    .person(Person.builder().build())
                    .skill(Skill.builder().build())
                    .skillLevel(SkillLevel.builder().build())
                    .build());
      }
      skillLevels.add(
          SkillLevel.builder()
              .skillLevelId(i)
              .skillLevelOrder(1L)
              .skillLevelDescription("Skill-" + i)
              .skillOwnerships(skillOwnerships)
              .build());
    }
    when(skillLevelRepository.findById(any())).thenReturn(Optional.ofNullable(skillLevels.get(0)));
    when(skillLevelRepository.findAll()).thenReturn(skillLevels);
    when(skillLevelRepository.save(any()))
        .thenAnswer(invocation -> invocation.getArgument(0, SkillLevel.class));
    when(skillLevelRepository.informativeDelete(1L)).thenReturn(1);
    skillLevelService = new SkillLevelService(skillLevelRepository, new ModelMapper());
  }

  /** Test find service method. */
  @Test
  @DisplayName("Find")
  void testFind() {
    SkillLevelDto result = skillLevelService.find(1L);
    assertEquals(1L, result.getSkillLevelId());
  }

  /** Test find service method when a skill level cannot be found. */
  @Test
  @DisplayName("Find - no skill level found")
  void testFindNoSkillLevel() {
    when(skillLevelRepository.findById(any())).thenReturn(Optional.empty());
    Exception exception =
        assertThrows(
            NoSuchElementException.class,
            () -> {
              skillLevelService.find(1L);
            });
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains("No skill level found with the ID: 1"));
  }

  /** Test get all service method. */
  @Test
  @DisplayName("Get all")
  void testGetAll() {
    List<SkillLevelDto> result = skillLevelService.getAll();
    assertEquals(10, result.size());
  }

  /** Test create service method. */
  @Test
  @DisplayName("Create")
  void testCreate() {
    SkillLevelDto result = skillLevelService.create(1L, "Skill-X");
    assertEquals("Skill-X", result.getSkillLevelDescription());
  }

  /** Test update service method (set e-mail). */
  @Test
  @DisplayName("Update (order)")
  void testUpdateOrder() {
    SkillLevelDto result = skillLevelService.update(1L, 1L, null);
    assertEquals(1, result.getSkillLevelOrder());
  }

  /** Test update service method (set e-mail). */
  @Test
  @DisplayName("Update (description)")
  void testUpdateDescription() {
    SkillLevelDto result = skillLevelService.update(1L, null, "Skill-X");
    assertEquals("Skill-X", result.getSkillLevelDescription());
  }

  /** Test update service method when a skill level cannot be found. */
  @Test
  @DisplayName("Update - no skill level found")
  void testUpdateNoSkillLevel() {
    when(skillLevelRepository.findById(any())).thenReturn(Optional.empty());
    Exception exception =
        assertThrows(
            NoSuchElementException.class,
            () -> {
              skillLevelService.update(1L, null, "Skill-X");
            });
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains("No skill level found with the ID: 1"));
  }

  /** Test delete service method. */
  @Test
  @DisplayName("Delete")
  void testDelete() {
    skillLevelService.delete(1L);
    verify(skillLevelRepository, times(1)).informativeDelete(1L);
  }

  /** Test delete service method when a skill level cannot be found. */
  @Test
  @DisplayName("Delete - no skill level found")
  void testDeleteNoSkillLevel() {
    when(skillLevelRepository.informativeDelete(1L)).thenReturn(0);
    Exception exception =
        assertThrows(
            NoSuchElementException.class,
            () -> {
              skillLevelService.delete(1L);
            });
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains("No skill level found with the ID: 1"));
  }
}
