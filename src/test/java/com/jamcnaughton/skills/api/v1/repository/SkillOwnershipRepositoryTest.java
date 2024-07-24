package com.jamcnaughton.skills.api.v1.repository;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.jamcnaughton.skills.model.entity.SkillOwnership;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/** Skill ownership repository tests. */
@DataJpaTest
@EntityScan("com.jamcnaughton.skills.model.entity")
@DBRider
@EnableAutoConfiguration
@Transactional(propagation = Propagation.SUPPORTS)
@DBUnit(cacheConnection = false, alwaysCleanAfter = true)
@DataSet(value = "skill-ownership.xml")
@TestPropertySource(value = {"classpath:application.properties"})
class SkillOwnershipRepositoryTest {

  @Autowired private SkillOwnershipRepository skillOwnershipRepository;

  /** Test find by person ID and skill ID query. */
  @Test
  @DisplayName("Find by person ID and skill ID.")
  void testFindByPersonIdAndSkillId() {
    SkillOwnership response = skillOwnershipRepository.findByPersonIdAndSkillId(1L, 1L).orElse(null);
    assertEquals(1, response.getSkillOwnershipId());
  }

  /** Test find by person ID and skill ID query when there's no record found. */
  @Test
  @DisplayName("Find by person ID and skill ID - no record found.")
  void testFindByPersonIdAndSkillIdNoRecordFound() {
    Optional<SkillOwnership> response = skillOwnershipRepository.findByPersonIdAndSkillId(20L, 20L);
    assertTrue(response.isEmpty());
  }

  /** Test informative delete repository query. */
  @Test
  @DisplayName("Informative delete.")
  void testInformativeDelete() {
    int response = skillOwnershipRepository.informativeDelete(1L, 1L);
    assertEquals(1, response);
  }

  /** Test informative delete repository query (skill ownership not found). */
  @Test
  @DisplayName("Informative delete - skill ownership not found.")
  void testInformativeDeleteSkillOwnershipNotFound() {
    int response = skillOwnershipRepository.informativeDelete(5L, 1L);
    assertEquals(0, response);
  }
}
