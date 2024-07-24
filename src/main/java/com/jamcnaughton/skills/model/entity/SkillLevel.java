package com.jamcnaughton.skills.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Model to represent a record in the skill_level table. */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "skill_level")
public class SkillLevel {

  /** ID of the skill level record. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "skill_level_id")
  @Schema(example = "1")
  private long skillLevelId;

  /** Index of this skill level record in the order of all skill levels. */
  @Column(name = "skill_level_order")
  @Schema(example = "1")
  private long skillLevelOrder;

  /** The skill level's description. */
  @Column(name = "skill_level_description")
  @Schema(example = "Leadership")
  private String skillLevelDescription;

  /** The skill ownership records affiliated to the skill level. */
  @OneToMany
  @JoinColumn(name = "skill_level_id")
  private Set<SkillOwnership> skillOwnerships = new HashSet<>();
}
