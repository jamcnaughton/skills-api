package com.jamcnaughton.skills.api.v1.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

/** Model to represent a person DTO. */
@Data
public class PersonDto {

  /** ID of the person. */
  private Long personId;

  /** The person's e-mail address. */
  private String email;

  /** The person's forenames. */
  private String forenames;

  /** The person's singular surname. */
  private String surname;

  /** The skill ownerships affiliated to the person. */
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Set<SkillOwnershipDto> skillOwnerships = new HashSet<>();
}
