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

/** Skill level repository tests. */
@DataJpaTest
@EntityScan("com.jamcnaughton.skills.model.entity")
@DBRider
@EnableAutoConfiguration
@Transactional(propagation = Propagation.SUPPORTS)
@DBUnit(cacheConnection = false)
@DataSet(value = "skill-level.xml")
@TestPropertySource(value = {"classpath:application.properties"})
class SkillLevelRepositoryTest {

  @Autowired private SkillLevelRepository skillLevelRepository;

  /** Test informative delete repository query. */
  @Test
  @DisplayName("Informative delete.")
  void testInformativeDelete() {
    int response = skillLevelRepository.informativeDelete(1L);
    assertEquals(1, response);
  }

  /** Test informative delete repository query (skill level not found). */
  @Test
  @DisplayName("Informative delete - skill level not found.")
  void testInformativeDeleteSkillLevelNotFound() {
    int response = skillLevelRepository.informativeDelete(20L);
    assertEquals(0, response);
  }
}
