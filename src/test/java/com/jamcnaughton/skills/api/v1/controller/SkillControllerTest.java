package com.jamcnaughton.skills.api.v1.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.jamcnaughton.skills.api.v1.service.SkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

/** Skill controller tests. */
@DisplayName("Skill Controller")
class SkillControllerTest {

  /** The controller to test. */
  private SkillController skillController;

  /** Mock of the service the controller uses. */
  @MockBean private SkillService skillService;

  /** Setup. */
  @BeforeEach
  void setup() {
    skillService = mock(SkillService.class);
    skillController = new SkillController(skillService);
  }

  /** Test find controller endpoint. */
  @Test
  @DisplayName("Find")
  void testFind() {
    skillController.find(1L);
    verify(skillService, times(1)).find(1L);
  }

  /** Test get all controller endpoint. */
  @Test
  @DisplayName("Get all")
  void testGetAll() {
    skillController.getAll(null);
    verify(skillService, times(1)).getAll(null);
  }

  /** Test create controller endpoint. */
  @Test
  @DisplayName("Create")
  void testCreate() {
    skillController.create("Test Skill");
    verify(skillService, times(1)).create("Test Skill");
  }

  /** Test update controller endpoint. */
  @Test
  @DisplayName("Update")
  void testUpdate() {
    skillController.update(1L, "Test Skill");
    verify(skillService, times(1)).update(1L, "Test Skill");
  }

  /** Test delete controller endpoint. */
  @Test
  @DisplayName("Delete")
  void testDelete() {
    skillController.delete(1L);
    verify(skillService, times(1)).delete(1L);
  }
}
