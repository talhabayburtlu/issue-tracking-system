package com.its.issuetrackingservice.persistence.entity;

import com.its.issuetrackingservice.domain.enums.IssueCategory;
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

	@Lob
	@Column(name = "description")
	private String description;

	@Column(name = "abbreviation", length = 10, nullable = false, unique = true)
	private String abbreviation;

	@Column(name = "number", nullable = false)
	private Long number;

	@Enumerated(EnumType.STRING)
	@Column(name = "category")
	private IssueCategory category;

	@Column(name = "points")
	private Integer points;

	@ManyToOne
	@JoinColumn(name = "state_id")
	private State state;

	@ManyToOne
	@JoinColumn(name = "sprint_id", nullable = false)
	private Sprint sprint;

	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	@ManyToOne
	@JoinColumn(name = "creator_user_id", nullable = false)
	private User creatorUser;

	@ManyToOne
	@JoinColumn(name = "verifier_user_id", nullable = false)
	private User verifierUser;

	@OneToMany(mappedBy = "issue")
	private List<Attachment> attachments;

	@OneToMany(mappedBy = "issue")
	private Set<Comment> comments;
}
