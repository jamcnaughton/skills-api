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

/** Model to represent a record in the person table. */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "person")
public class Person {

  /** ID of the person record. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "person_id")
  @Schema(example = "1")
  private Long personId;

  /** The person's e-mail address. */
  @Column(name = "email")
  @Schema(example = "example@dummy.com")
  private String email;

  /** The person's forenames. */
  @Column(name = "forenames")
  @Schema(example = "James")
  private String forenames;

  /** The person's singular surname. */
  @Column(name = "surname")
  @Schema(example = "Cook")
  private String surname;

  /** The skill ownership records affiliated to the person. */
  @OneToMany
  @JoinColumn(name = "person_id")
  private Set<SkillOwnership> skillOwnerships = new HashSet<>();
}
