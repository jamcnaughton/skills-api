package com.jamcnaughton.skills.api.v1.service;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jamcnaughton.skills.api.v1.model.PersonDto;
import com.jamcnaughton.skills.api.v1.repository.PersonRepository;
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

/** Person service tests. */
@DisplayName("Person Service")
public class PersonServiceTest {
  private final PersonRepository personRepository = mock(PersonRepository.class);
  private PersonService personService;

  /** Setup. */
  @BeforeEach
  void setup() {
    ArrayList<Person> persons = new ArrayList<>();
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
      persons.add(
          Person.builder()
              .personId(i)
              .email("test" + i + "@dummy.com")
              .forenames("Forenames-" + i)
              .surname("Surnames-" + i)
              .skillOwnerships(skillOwnerships)
              .build());
    }
    when(personRepository.findById(any())).thenReturn(Optional.ofNullable(persons.get(0)));
    when(personRepository.findAll()).thenReturn(persons);
    when(personRepository.save(any()))
        .thenAnswer(invocation -> invocation.getArgument(0, Person.class));
    when(personRepository.informativeDelete(1L)).thenReturn(1);
    personService = new PersonService(personRepository, new ModelMapper());
  }

  /** Test find service method. */
  @Test
  @DisplayName("Find")
  void testFind() {
    PersonDto result = personService.find(1L);
    assertEquals(1L, result.getPersonId());
  }

  /** Test find service method when a person cannot be found. */
  @Test
  @DisplayName("Find - no person found")
  void testFindNoPerson() {
    when(personRepository.findById(any())).thenReturn(Optional.empty());
    Exception exception =
        assertThrows(
            NoSuchElementException.class,
            () -> {
              personService.find(1L);
            });
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains("No person found with the ID: 1"));
  }

  /** Test get all service method. */
  @Test
  @DisplayName("Get all")
  void testGetAll() {
    List<PersonDto> result = personService.getAll();
    assertEquals(10, result.size());
  }

  /** Test create service method. */
  @Test
  @DisplayName("Create")
  void testCreate() {
    PersonDto result = personService.create("testX@dummy.com", "Forename-X", "Surname-X");
    assertEquals("testX@dummy.com", result.getEmail());
  }

  /** Test update service method (set e-mail). */
  @Test
  @DisplayName("Update (email)")
  void testUpdateEmail() {
    PersonDto result = personService.update(1L, "testX@dummy.com", null, null);
    assertEquals("testX@dummy.com", result.getEmail());
  }

  /** Test update service method (set forenames). */
  @Test
  @DisplayName("Update (forenames)")
  void testUpdateForenames() {
    PersonDto result = personService.update(1L, null, "Forename-X", null);
    assertEquals("Forename-X", result.getForenames());
  }

  /** Test update service method (set surname). */
  @Test
  @DisplayName("Update (surname)")
  void testUpdateSurname() {
    PersonDto result = personService.update(1L, null, null, "Surname-X");
    assertEquals("Surname-X", result.getSurname());
  }

  /** Test update service method when a person cannot be found. */
  @Test
  @DisplayName("Update - no person found")
  void testUpdateNoPerson() {
    when(personRepository.findById(any())).thenReturn(Optional.empty());
    Exception exception =
        assertThrows(
            NoSuchElementException.class,
            () -> {
              personService.update(1L, "testX@dummy.com", null, null);
            });
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains("No person found with the ID: 1"));
  }

  /** Test delete service method. */
  @Test
  @DisplayName("Delete")
  void testDelete() {
    personService.delete(1L);
    verify(personRepository, times(1)).informativeDelete(1L);
  }

  /** Test delete service method when a person cannot be found. */
  @Test
  @DisplayName("Delete - no person found")
  void testDeleteNoPerson() {
    when(personRepository.informativeDelete(1L)).thenReturn(0);
    Exception exception =
        assertThrows(
            NoSuchElementException.class,
            () -> {
              personService.delete(1L);
            });
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains("No person found with the ID: 1"));
  }
}
