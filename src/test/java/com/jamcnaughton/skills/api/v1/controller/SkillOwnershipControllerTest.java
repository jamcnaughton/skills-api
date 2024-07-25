package com.jamcnaughton.skills.api.v1.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.jamcnaughton.skills.api.v1.service.SkillOwnershipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

/** Skill ownership controller tests. */
@DisplayName("Skill Ownership Controller")
class SkillOwnershipControllerTest {

  /** The controller to test. */
  private SkillOwnershipController skillOwnershipController;

  /** Mock of the service the controller uses. */
  @MockBean private SkillOwnershipService skillOwnershipService;

  /** Setup. */
  @BeforeEach
  void setup() {
    skillOwnershipService = mock(SkillOwnershipService.class);
    skillOwnershipController = new SkillOwnershipController(skillOwnershipService);
  }

  /** Test find controller endpoint. */
  @Test
  @DisplayName("Find")
  void testFind() {
    skillOwnershipController.find(1L, 1L);
    verify(skillOwnershipService, times(1)).find(1L, 1L);
  }

  /** Test get all controller endpoint. */
  @Test
  @DisplayName("Get all")
  void testGetAll() {
    skillOwnershipController.getAll(null);
    verify(skillOwnershipService, times(1)).getAll(null);
  }

  /** Test create controller endpoint. */
  @Test
  @DisplayName("Create")
  void testCreate() {
    skillOwnershipController.create(1L, 1L, 1L);
    verify(skillOwnershipService, times(1)).create(1L, 1L, 1L);
  }

  /** Test update controller endpoint. */
  @Test
  @DisplayName("Update")
  void testUpdate() {
    skillOwnershipController.update(1L, 1L, 1L);
    verify(skillOwnershipService, times(1)).update(1L, 1L, 1L);
  }

  /** Test delete controller endpoint. */
  @Test
  @DisplayName("Delete")
  void testDelete() {
    skillOwnershipController.delete(1L, 1L);
    verify(skillOwnershipService, times(1)).delete(1L, 1L);
  }
}
