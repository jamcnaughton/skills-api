package com.jamcnaughton.skills.api.v1.repository;

import com.jamcnaughton.skills.model.entity.SkillLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** Repository for skill level requests. */
@Repository
public interface SkillLevelRepository extends JpaRepository<SkillLevel, Long> {

  /**
   * Delete the specified skill level in a manner which returns information on whether a delete took
   * place.
   *
   * @param skillLevelId The ID of the skill level to delete.
   * @return The result of the deletion, 1 if successful, otherwise 0.
   */
  @Modifying
  @Transactional
  @Query(
      value =
          """
          DELETE FROM skill_level p
          WHERE p.skill_level_id = :skill_level_id
          """,
      nativeQuery = true)
  int informativeDelete(@Param("skill_level_id") long skillLevelId);
}
