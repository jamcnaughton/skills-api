package com.jamcnaughton.skills.api.v1.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

/** Model to represent a skill table. */
@Data
public class SkillDto {

  /** ID of the skill. */
  private long skillId;

  /** The skill's name. */
  private String skillName;

  /** The skill ownerships affiliated to the skill. */
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Set<SkillOwnershipDto> skillOwnerships = new HashSet<>();
}
