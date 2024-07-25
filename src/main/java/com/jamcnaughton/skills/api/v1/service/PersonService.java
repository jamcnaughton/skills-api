package com.jamcnaughton.skills.api.v1.service;

import com.jamcnaughton.skills.api.v1.model.PersonDto;
import com.jamcnaughton.skills.api.v1.repository.PersonRepository;
import com.jamcnaughton.skills.model.entity.Person;
import com.jamcnaughton.skills.model.entity.SkillOwnership;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

/** Service for persons. */
@Service
@AllArgsConstructor
public class PersonService {

  /** Repository for handling person related queries. */
  private final PersonRepository personRepository;

  /** Bean for converting between entities and DTOs. */
  @Autowired private ModelMapper modelMapper;

  /**
   * Find a specific person.
   *
   * @param personId The ID of the person to return.
   * @return The specified person.
   */
  public PersonDto find(Long personId) {
    return convertToDto(get(personId));
  }

  /**
   * Get all persons.
   *
   * @param pageable The object that enables pagination
   * @return All persons.
   */
  public PagedModel<PersonDto> getAll(final Pageable pageable) {
    return new PagedModel<>(personRepository.findAll(pageable)
        .map(person -> convertToDto(person)));
  }

  /**
   * Create a new person.
   *
   * @param email The email address of the new person.
   * @param forenames The forename(s) of the new person.
   * @param surname The surname address of the new person.
   * @return The created person.
   */
  public PersonDto create(String email, String forenames, String surname) {
    Person person = new Person();
    person.setEmail(email);
    person.setForenames(forenames);
    person.setSurname(surname);
    return convertToDto(personRepository.save(person));
  }

  /**
   * Update an existing person.
   *
   * @param personId The ID of the person to update.
   * @param email The new email address of the person (null if not to be changed).
   * @param forenames The new forename(s) of the person (null if not to be changed).
   * @param surname The new surname address of the person (null if not to be changed).
   * @return The updated person.
   */
  public PersonDto update(Long personId, String email, String forenames, String surname) {
    Person person = get(personId);
    if (email != null) {
      person.setEmail(email);
    }
    if (forenames != null) {
      person.setForenames(forenames);
    }
    if (surname != null) {
      person.setSurname(surname);
    }
    return convertToDto(personRepository.save(person));
  }

  /**
   * Delete a specific person.
   *
   * @param personId The ID of the person to delete.
   */
  public void delete(Long personId) {
    int result = personRepository.informativeDelete(personId);
    if (result != 1) {
      throw new NoSuchElementException("No person found with the ID: " + personId);
    }
  }

  /**
   * Find a specific person.
   *
   * @param personId The ID of the person to return.
   * @return The specified person.
   */
  private Person get(Long personId) {
    return personRepository
        .findById(personId)
        .orElseThrow(() -> new NoSuchElementException("No person found with the ID: " + personId));
  }

  /**
   * Tidy a person entity then convert it into a person DTO.
   *
   * @param person The entity to convert.
   * @return The DTO based on the entity.
   */
  private PersonDto convertToDto(Person person) {
    if (person.getSkillOwnerships() != null) {
      for (SkillOwnership skillOwnership : person.getSkillOwnerships()) {
        skillOwnership.setPerson(null);
        skillOwnership.getSkill().setSkillOwnerships(null);
        skillOwnership.getSkillLevel().setSkillOwnerships(null);
      }
    }
    return modelMapper.map(person, PersonDto.class);
  }
}
