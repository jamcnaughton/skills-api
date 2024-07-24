package com.jamcnaughton.skills.api.v1.repository;

import com.jamcnaughton.skills.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** Repository for person requests. */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

  /**
   * Delete the specified person in a manner which returns information on whether a delete took
   * place.
   *
   * @param personId The ID of the person to delete.
   * @return The result of the deletion, 1 if successful, otherwise 0.
   */
  @Modifying
  @Transactional
  @Query(
      value =
          """
          DELETE FROM person p
          WHERE p.person_id = :person_id
          """,
      nativeQuery = true)
  int informativeDelete(@Param("person_id") long personId);
}
