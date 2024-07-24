package com.jamcnaughton.skills.api.v1.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.jamcnaughton.skills.api.v1.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

/** Person controller tests. */
@DisplayName("Person Controller")
class PersonControllerTest {

  /** The controller to test. */
  private PersonController personController;

  /** Mock of the service the controller uses. */
  @MockBean private PersonService personService;

  /** Setup. */
  @BeforeEach
  void setup() {
    personService = mock(PersonService.class);
    personController = new PersonController(personService);
  }

  /** Test find controller endpoint. */
  @Test
  @DisplayName("Find")
  void testFind() {
    personController.find(1L);
    verify(personService, times(1)).find(1L);
  }

  /** Test get all controller endpoint. */
  @Test
  @DisplayName("Get all")
  void testGetAll() {
    personController.getAll();
    verify(personService, times(1)).getAll();
  }

  /** Test create controller endpoint. */
  @Test
  @DisplayName("Create")
  void testCreate() {
    personController.create("test1@dummy.com", "Forenames-1", "Surnames-1");
    verify(personService, times(1)).create("test1@dummy.com", "Forenames-1", "Surnames-1");
  }

  /** Test update controller endpoint. */
  @Test
  @DisplayName("Update")
  void testUpdate() {
    personController.update(1L, "test1@dummy.com", "Forenames-1", "Surnames-1");
    verify(personService, times(1)).update(1L, "test1@dummy.com", "Forenames-1", "Surnames-1");
  }

  /** Test delete controller endpoint. */
  @Test
  @DisplayName("Delete")
  void testDelete() {
    personController.delete(1L);
    verify(personService, times(1)).delete(1L);
  }
}
