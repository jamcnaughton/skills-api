package com.jamcnaughton.skills.api.v1.repository;

import com.jamcnaughton.skills.model.entity.SkillOwnership;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** Repository for skill ownership requests. */
@Repository
public interface SkillOwnershipRepository extends JpaRepository<SkillOwnership, Long> {

  /**
   * Find a specific skill ownership.
   *
   * @param personId The ID of the person affiliated with the skill ownership to return.
   * @param skillId The ID of the skill affiliated with the skill ownership to return.
   * @return The specified skill ownership.
   */
  Optional<SkillOwnership> findByPersonIdAndSkillId(Long personId, Long skillId);

  /**
   * Delete the specified skillOwnership in a manner which returns information on whether a delete
   * took place.
   *
   * @param personId The ID of the person affiliated with the skill ownership to delete.
   * @param skillId The ID of the skill affiliated with the skill ownership to delete.
   * @return The result of the deletion, 1 if successful, otherwise 0.
   */
  @Modifying
  @Transactional
  @Query(
      value =
          """
          DELETE FROM skill_ownership so
          WHERE so.person_id = :person_id
          AND so.skill_id = :skill_id
          """,
      nativeQuery = true)
  int informativeDelete(@Param("person_id") long personId, @Param("skill_id") long skillId);
}
