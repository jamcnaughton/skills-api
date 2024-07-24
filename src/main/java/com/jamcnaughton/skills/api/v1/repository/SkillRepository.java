package com.jamcnaughton.skills.api.v1.repository;

import com.jamcnaughton.skills.model.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** Repository for skill requests. */
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

  /**
   * Delete the specified skill in a manner which returns information on whether a delete took
   * place.
   *
   * @param skillId The ID of the skill to delete.
   * @return The result of the deletion, 1 if successful, otherwise 0.
   */
  @Modifying
  @Transactional
  @Query(
      value =
          """
                    DELETE FROM skill s
                    WHERE s.skill_id = :skill_id
                    """,
      nativeQuery = true)
  int informativeDelete(@Param("skill_id") long skillId);
}
