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

/** Skill repository tests. */
@DataJpaTest
@EntityScan("com.jamcnaughton.skills.model.entity")
@DBRider
@EnableAutoConfiguration
@Transactional(propagation = Propagation.SUPPORTS)
@DBUnit(cacheConnection = false)
@DataSet(value = "skill.xml")
@TestPropertySource(value = {"classpath:application.properties"})
class SkillRepositoryTest {

  @Autowired private SkillRepository skillRepository;

  /** Test informative delete repository query. */
  @Test
  @DisplayName("Informative delete.")
  void testInformativeDelete() {
    int response = skillRepository.informativeDelete(1L);
    assertEquals(1, response);
  }

  /** Test informative delete repository query (skill not found). */
  @Test
  @DisplayName("Informative delete - skill not found.")
  void testInformativeDeleteSkillNotFound() {
    int response = skillRepository.informativeDelete(20L);
    assertEquals(0, response);
  }
}
