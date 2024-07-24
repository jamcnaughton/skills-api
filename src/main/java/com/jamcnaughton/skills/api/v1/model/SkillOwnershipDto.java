package com.jamcnaughton.skills.api.v1.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/** Model to represent a skill ownership DTO. */
@Data
public class SkillOwnershipDto {

  /** ID of the related skill ownership record. */
  private long skillOwnershipId;

  /** The person affiliated with this skill ownership. */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private PersonDto person;

  /** The skill affiliated with this skill ownership. */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private SkillDto skill;

  /** The skill level affiliated with this skill ownership. */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private SkillLevelDto skillLevel;
}
