package com.jamcnaughton.skills.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/** Model to represent a record in the skill_ownership table. */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "skill_ownership")
public class SkillOwnership {

  /** ID of the skill ownership record. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "skill_ownership_id")
  @Schema(example = "1")
  private long skillOwnershipId;

  /** ID of the related person record. */
  @Column(name = "person_id")
  @JsonIgnore
  @Schema(example = "1")
  private Long personId;

  /** ID of the related skill record. */
  @Column(name = "skill_id")
  @JsonIgnore
  @Schema(example = "1")
  private long skillId;

  /** ID of the related skill level record. */
  @Column(name = "skill_level_id")
  @JsonIgnore
  @Schema(example = "1")
  private long skillLevelId;

  /** The person affiliated with this skill ownership record. */
  @OneToOne(cascade = CascadeType.ALL)
  @JsonIgnoreProperties({"skillOwnerships"})
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(
      name = "person_id",
      referencedColumnName = "person_id",
      insertable = false,
      updatable = false)
  private Person person;

  /** The skill affiliated with this skill ownership record. */
  @OneToOne(cascade = CascadeType.ALL)
  @JsonIgnoreProperties({"skillOwnerships"})
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(
      name = "skill_id",
      referencedColumnName = "skill_id",
      insertable = false,
      updatable = false)
  private Skill skill;

  /** The skill level affiliated with this skill ownership record. */
  @OneToOne(cascade = CascadeType.ALL)
  @JsonIgnoreProperties({"skillOwnerships"})
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(
      name = "skill_level_id",
      referencedColumnName = "skill_level_id",
      insertable = false,
      updatable = false)
  private SkillLevel skillLevel;
}
