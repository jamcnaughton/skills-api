package com.jamcnaughton.skills.api.v1.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/** Person repository tests. */
@DataJpaTest
@EntityScan("com.jamcnaughton.skills.model.entity")
@DBRider
@EnableAutoConfiguration
@Transactional(propagation = Propagation.SUPPORTS)
@DBUnit(cacheConnection = false)
@DataSet(value = "person.xml")
@TestPropertySource(value = {"classpath:application.properties"})
class PersonRepositoryTest {

  @Autowired private PersonRepository personRepository;

  /** Test informative delete repository query. */
  @Test
  @DisplayName("Informative delete.")
  void testInformativeDelete() {
    int response = personRepository.informativeDelete(1L);
    assertEquals(1, response);
  }

  /** Test informative delete repository query (person not found). */
  @Test
  @DisplayName("Informative delete - person not found.")
  void testInformativeDeletePersonNotFound() {
    int response = personRepository.informativeDelete(20L);
    assertEquals(0, response);
  }
}
