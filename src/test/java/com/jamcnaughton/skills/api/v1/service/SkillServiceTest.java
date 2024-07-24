package com.jamcnaughton.skills.api.v1.service;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jamcnaughton.skills.api.v1.model.SkillDto;
import com.jamcnaughton.skills.api.v1.repository.SkillRepository;
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

/** Skill service tests. */
@DisplayName("Skill Service")
public class SkillServiceTest {
  private final SkillRepository skillRepository = mock(SkillRepository.class);
  private SkillService skillService;
  private ArrayList<Skill> skills;

  /** Setup. */
  @BeforeEach
  void setup() {
    skills = new ArrayList<>();
    for (long i = 1; i <= 10; i++) {
      Set<SkillOwnership> skillOwnerships = null;
      if (i % 2 == 0) {
        skillOwnerships =
            Sets.set(
                SkillOwnership.builder()
                    .skill(Skill.builder().build())
                    .person(Person.builder().build())
                    .skillLevel(SkillLevel.builder().build())
                    .build());
      }
      skills.add(
          Skill.builder()
              .skillId(i)
              .skillName("Test Skill")
              .skillOwnerships(skillOwnerships)
              .build());
    }
    when(skillRepository.findById(any())).thenReturn(Optional.ofNullable(skills.get(0)));
    when(skillRepository.findAll()).thenReturn(skills);
    when(skillRepository.save(any()))
        .thenAnswer(invocation -> invocation.getArgument(0, Skill.class));
    when(skillRepository.informativeDelete(1L)).thenReturn(1);
    skillService = new SkillService(skillRepository, new ModelMapper());
  }

  /** Test find service method. */
  @Test
  @DisplayName("Find")
  void testFind() {
    SkillDto result = skillService.find(1L);
    assertEquals(1L, result.getSkillId());
  }

  /** Test find service method when a skill cannot be found. */
  @Test
  @DisplayName("Find - no skill found")
  void testFindNoSkill() {
    when(skillRepository.findById(any())).thenReturn(Optional.empty());
    Exception exception =
        assertThrows(
            NoSuchElementException.class,
            () -> {
              skillService.find(1L);
            });
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains("No skill found with the ID: 1"));
  }

  /** Test get all service method. */
  @Test
  @DisplayName("Get all")
  void testGetAll() {
    List<SkillDto> result = skillService.getAll();
    assertEquals(10, result.size());
  }

  /** Test create service method. */
  @Test
  @DisplayName("Create")
  void testCreate() {
    SkillDto result = skillService.create("Test Skill");
    assertEquals("Test Skill", result.getSkillName());
  }

  /** Test update service method (set e-mail). */
  @Test
  @DisplayName("Update")
  void testUpdateEmail() {
    SkillDto result = skillService.update(1L, "Test Skill");
    assertEquals("Test Skill", result.getSkillName());
  }

  /** Test update service method when a skill cannot be found. */
  @Test
  @DisplayName("Update - no skill found")
  void testUpdateNoSkill() {
    when(skillRepository.findById(any())).thenReturn(Optional.empty());
    Exception exception =
        assertThrows(
            NoSuchElementException.class,
            () -> {
              skillService.update(1L, "Test Skill");
            });
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains("No skill found with the ID: 1"));
  }

  /** Test delete service method. */
  @Test
  @DisplayName("Delete")
  void testDelete() {
    skillService.delete(1L);
    verify(skillRepository, times(1)).informativeDelete(1L);
  }

  /** Test delete service method when a skill cannot be found. */
  @Test
  @DisplayName("Delete - no skill found")
  void testDeleteNoSkill() {
    when(skillRepository.informativeDelete(1L)).thenReturn(0);
    Exception exception =
        assertThrows(
            NoSuchElementException.class,
            () -> {
              skillService.delete(1L);
            });
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains("No skill found with the ID: 1"));
  }
}
