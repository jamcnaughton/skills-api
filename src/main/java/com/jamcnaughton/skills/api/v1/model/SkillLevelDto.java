package com.jamcnaughton.skills.api.v1.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

/** Model to represent a skill level. */
@Data
public class SkillLevelDto {

  /** ID of the skill level. */
  private long skillLevelId;

  /** Index of this skill level in the order of all skill levels. */
  private long skillLevelOrder;

  /** The skill level's description. */
  private String skillLevelDescription;

  /** The skill ownerships affiliated to the skill level. */
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Set<SkillOwnershipDto> skillOwnerships = new HashSet<>();
}
