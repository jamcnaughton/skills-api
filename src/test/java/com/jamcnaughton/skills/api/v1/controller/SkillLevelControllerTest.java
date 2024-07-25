package com.jamcnaughton.skills.api.v1.controller;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.jamcnaughton.skills.api.v1.service.SkillLevelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

/** Skill level controller tests. */
@DisplayName("Skill Level Controller")
class SkillLevelControllerTest {

  /** The controller to test. */
  private SkillLevelController skillLevelController;

  /** Mock of the service the controller uses. */
  @MockBean private SkillLevelService skillLevelService;

  /** Setup. */
  @BeforeEach
  void setup() {
    skillLevelService = mock(SkillLevelService.class);
    skillLevelController = new SkillLevelController(skillLevelService);
  }

  /** Test find controller endpoint. */
  @Test
  @DisplayName("Find")
  void testFind() {
    skillLevelController.find(1L);
    verify(skillLevelService, times(1)).find(1L);
  }

  /** Test get all controller endpoint. */
  @Test
  @DisplayName("Get all")
  void testGetAll() {
    skillLevelController.getAll(null);
    verify(skillLevelService, times(1)).getAll(null);
  }

  /** Test create controller endpoint. */
  @Test
  @DisplayName("Create")
  void testCreate() {
    skillLevelController.create(1L, "Description-1");
    verify(skillLevelService, times(1)).create(1L, "Description-1");
  }

  /** Test update controller endpoint. */
  @Test
  @DisplayName("Update")
  void testUpdate() {
    skillLevelController.update(1L, 1L, "Description-1");
    verify(skillLevelService, times(1)).update(1L, 1L, "Description-1");
  }

  /** Test update controller endpoint when no parameters are supplied. */
  @Test
  @DisplayName("Update - no parameters")
  void testUpdateNoParameters() {
    Exception exception =
            assertThrows(
                    IllegalArgumentException.class,
                    () -> {
                      skillLevelController.update(1L, null, null);
                    });
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains("No order or description parameters supplied."));
  }

  /** Test delete controller endpoint. */
  @Test
  @DisplayName("Delete")
  void testDelete() {
    skillLevelController.delete(1L);
    verify(skillLevelService, times(1)).delete(1L);
  }
}
