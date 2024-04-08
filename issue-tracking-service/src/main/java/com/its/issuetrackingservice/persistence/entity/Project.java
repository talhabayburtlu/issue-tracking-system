package com.its.issuetrackingservice.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.context.annotation.Lazy;

import java.util.Set;

@Entity()
@Table(name = "project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Project extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_sequence")
	@SequenceGenerator(name = "project_sequence", sequenceName = "project_sequence", allocationSize = 1)
	@Column(name = "id", precision = 18)
	private Long id;

	@NotBlank
	@Column(name = "name", length = 30, nullable = false)
	private String name;

	@NotBlank
	@Column(name = "abbreviation", length = 5)
	private String abbreviation;

	@Column(name = "keycloak_id", nullable = false, unique = true, length = 36)
	private String keycloakId;

	@Column(name = "is_active", nullable = false)
	private Boolean isActive;

	@ManyToOne
	@JoinColumn(name = "parent_project_id")
	@Lazy
	private Project parentProject;

	@ManyToMany
	@JoinTable(
			name = "project_users",
			joinColumns = @JoinColumn(name = "project_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	@Lazy
	private Set<User> users;

	@OneToMany(mappedBy = "project")
	private Set<State> states;
}
