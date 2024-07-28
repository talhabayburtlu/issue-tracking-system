package com.its.issuetrackingservice.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity()
@Table(name = "issue")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Issue extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_sequence")
	@SequenceGenerator(name = "issue_sequence", sequenceName = "issue_sequence", allocationSize = 1)
	@Column(name = "id", precision = 18)
	private Long id;

	@NotBlank
	@Column(name = "title", length = 30, nullable = false)
	private String title;

	@NotBlank
	@Column(name = "code", length = 10, nullable = false)
	private String code;

	@Lob
	@Column(name = "description")
	private String description;

	@Column(name = "estimation", nullable = false)
	private Long estimation;

	@Column(name = "spentTime", nullable = false)
	private Long spentTime;

	@ManyToOne
	@Column(name = "category")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "state_id")
	private State state;

	@ManyToOne
	@JoinColumn(name = "sprint_id", nullable = false)
	private Sprint sprint;

	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	@OneToMany(mappedBy = "issue")
	private Set<Activity> activities;

	@OneToMany(mappedBy = "issue")
	private Set<Participation> participants;

	@OneToMany(mappedBy = "issue")
	private List<IssueSubsystem> subsystems;

	@OneToMany(mappedBy = "issue")
	private List<IssueAttachment> attachments;
}
