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

/** Model to represent a record in the skill table. */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "skill")
public class Skill {

  /** ID of the skill record. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "skill_id")
  @Schema(example = "1")
  private long skillId;

  /** The skill's name. */
  @Column(name = "skillName")
  @Schema(example = "Leadership")
  private String skillName;

  /** The skill ownership records affiliated to the skill. */
  @OneToMany
  @JoinColumn(name = "skill_id")
  private Set<SkillOwnership> skillOwnerships = new HashSet<>();
}
